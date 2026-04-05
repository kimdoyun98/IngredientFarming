import os
import requests
from google import genai

class GeminiCodeReview:
    def __init__(self):
        self.github_token = os.getenv("GITHUB_TOKEN")
        self.gemini_api_key = os.getenv("GEMINI_API_KEY")
        self.pr_number = os.getenv("PR_NUMBER")
        self.repo = os.getenv("GITHUB_REPOSITORY")

        if not all([self.github_token, self.gemini_api_key, self.pr_number, self.repo]):
            raise ValueError("환경 변수가 제대로 설정되지 않았습니다.")

    # 1. PR 파일 목록 가져오기
    def get_pr_files(self):
        url = f"https://api.github.com/repos/{self.repo}/pulls/{self.pr_number}/files"
        headers = {
            "Authorization": f"token {self.github_token}",
            "Accept": "application/vnd.github.v3+json"
        }

        res = requests.get(url, headers=headers)
        if res.status_code != 200:
            raise Exception(f"PR 파일 조회 실패: {res.text}")

        return res.json()

    # 2. 파일 diff 묶기 (chunking)
    def chunk_files(self, files, max_length=3000):
        chunks = []
        current_chunk = []
        current_length = 0

        for file in files:
            patch = file.get("patch")
            if not patch:
                continue

            patch_length = len(patch)

            # 큰 파일은 단독 처리
            if patch_length > max_length:
                if current_chunk:
                    chunks.append(current_chunk)
                    current_chunk = []
                    current_length = 0

                chunks.append([file])
                continue

            # 현재 chunk에 추가 가능하면 추가
            if current_length + patch_length <= max_length:
                current_chunk.append(file)
                current_length += patch_length
            else:
                chunks.append(current_chunk)
                current_chunk = [file]
                current_length = patch_length

        if current_chunk:
            chunks.append(current_chunk)

        return chunks

    def retry_with_backoff(self, func, max_retries=5, base_delay=1):
        for attempt in range(max_retries):
            try:
                return func()
            except Exception as e:
                is_last = attempt == max_retries - 1

                # 503 / UNAVAILABLE 같은 경우만 retry
                if "503" in str(e) or "UNAVAILABLE" in str(e):
                    if is_last:
                        raise

                    delay = base_delay * (2 ** attempt)
                    jitter = random.uniform(0, 0.5)  # thundering herd 방지
                    sleep_time = delay + jitter

                    print(f"⚠️ Gemini 과부하, {sleep_time:.2f}s 후 재시도 ({attempt+1}/{max_retries})")
                    time.sleep(sleep_time)
                else:
                    # 다른 에러는 바로 실패
                    raise

    # 3. Gemini 호출
    def call_gemini(self, files_chunk):
        client = genai.Client(api_key=self.gemini_api_key)

        diff_text = ""
        for f in files_chunk:
            filename = f.get("filename")
            patch = f.get("patch", "")
            diff_text += f"\n\n### File: {filename}\n{patch}"

        prompt = f"""
            You are a senior Android developer.
            리뷰는 한국어로 부탁해.
            Review this PR diff:
            
            {diff_text}
            
            Focus on:
            - MVI 구조
            - Coroutine / Flow misuse
            - 메모리 누수
            - 성능 문제
            - 코드 가독성
            - Compose 구조
            
            Return:
            1. 🔴 Critical Issues
            2. 🟡 Improvements
            3. ✅ Good Points
        """

        def request():
            response = client.models.generate_content(
                model="gemini-3-flash-preview",
                contents=prompt
            )
            return getattr(response, "text", "❌ AI 응답 없음")

        return self.retry_with_backoff(request)

    # 3. PR 코멘트 등록
    def comment_pr(self, review):
        url = f"https://api.github.com/repos/{self.repo}/issues/{self.pr_number}/comments"

        headers = {
            "Authorization": f"token {self.github_token}",
            "Accept": "application/vnd.github.v3+json"
        }

        data = {
            "body": f"## 🤖 AI Code Review\n\n{review}"
        }

        res = requests.post(url, headers=headers, json=data)

        if res.status_code != 201:
            raise Exception(f"PR 코멘트 등록 실패: {res.text}")

    # 전체 실행
    def run(self):
        print("🚀 AI 코드 리뷰 시작")

        files = self.get_pr_files()
        print(f"✅ 파일 {len(files)}개 가져오기 완료")

        chunks = self.chunk_files(files)
        print(f"✅ {len(chunks)}개의 chunk로 분할")

        final_review = ""

        for i, chunk in enumerate(chunks):
            print(f"🤖 Gemini 요청 중... ({i+1}/{len(chunks)})")
            review = self.call_gemini(chunk)
            final_review += f"\n\n---\n\n### 🔹 Chunk {i+1}\n{review}"

        self.comment_pr(final_review)
        print("✅ PR 코멘트 등록 완료")


if __name__ == "__main__":
    GeminiCodeReview().run()
