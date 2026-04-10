import os
import requests
from typing import List, Dict

class GeminiCodeReview:

    def __init__(self):
        # -------------------------
        # 환경 변수
        # -------------------------
        self.github_token = os.getenv("GITHUB_TOKEN")
        self.api_key = os.getenv("GEMINI_API_KEY")
        self.pr_number = os.getenv("PR_NUMBER")
        self.repo = os.getenv("GITHUB_REPOSITORY")

        # -------------------------
        # API URL
        # -------------------------
        self.github_api_base = "https://api.github.com"
        self.gemini_model = "gemini-3-flash-preview"
        self.gemini_url = f"https://generativelanguage.googleapis.com/v1beta/models/{self.gemini_model}:generateContent?key={self.api_key}"

        # -------------------------
        # 우선순위
        # -------------------------
        self.priority = {
            "domain": 1,
            "presentation_vm": 2,
            "presentation_ui": 3
        }

    # -------------------------
    # 1. PR 파일 가져오기
    # -------------------------
    def get_pr_files(self) -> List[Dict]:
        url = f"{self.github_api_base}/repos/{self.repo}/pulls/{self.pr_number}/files"

        headers = {
            "Authorization": f"Bearer {self.github_token}",
            "Accept": "application/vnd.github+json"
        }

        response = requests.get(url, headers=headers)
        response.raise_for_status()

        return response.json()

    # -------------------------
    # 2. 파일 분류
    # -------------------------
    def classify_file(self, file_path: str) -> str:
        if file_path.endswith("UseCase.kt"):
            return "domain"
        elif file_path.endswith("RepositoryImpl.kt"):
            return "data"
        elif file_path.endswith("ViewModel.kt"):
            return "presentation_vm"
        elif file_path.endswith("Screen.kt"):
            return "presentation_ui"
        else:
            return "ignore"

    # -------------------------
    # 3. 의미 있는 Diff 추출
    # -------------------------
    def extract_meaningful_diff(self, patch: str) -> str:
        if not patch:
            return ""

        keywords = [
            "if", "when", "for", "while",
            "return",
            "suspend",
            "Flow",
            "collect",
            "launch",
            "emit",
            "="
        ]

        lines = patch.split("\n")
        filtered = []

        for line in lines:
            if line.startswith("+") or line.startswith("-"):
                if any(k in line for k in keywords):
                    filtered.append(line)

        return "\n".join(filtered)

    # -------------------------
    # 4. Chunk 분리
    # -------------------------
    def split_chunks(self, code: str, max_size: int = 1500) -> List[str]:
        return [code[i:i + max_size] for i in range(0, len(code), max_size)]

    # -------------------------
    # 5. 한글 프롬프트
    # -------------------------
    def build_prompt(self, code: str, category: str) -> str:
        base = """
            당신은 시니어 안드로이드 개발자입니다.
            
            아래 규칙에 따라 코드 리뷰를 진행하세요.
            서론은 생략하고 문제만 간결하게 지적하세요.
            불필요한 설명, 칭찬, 요약은 하지 마세요.
        """

        if category == "domain":
            rule = """
                [Domain 리뷰]
                - 비즈니스 로직이 올바른지
                - Domain 책임 위반 여부
            """
        elif category == "presentation_vm":
            rule = """
                [ViewModel 리뷰]
                - 상태 관리 문제
                - Coroutine / Flow 오용
            """
        elif category == "presentation_ui":
            rule = """
                [Compose 리뷰]
                - 잘못된 Compose 사용
                - recomposition 문제
                - state hoisting 문제
            """
        else:
            return None

        return base + rule + "\n\n[코드]\n" + code

    # -------------------------
    # 6. Gemini 호출
    # -------------------------
    def call_gemini(self, prompt: str) -> str:
        data = {
            "contents": [
                {
                    "parts": [{"text": prompt}]
                }
            ]
        }

        try:
            response = requests.post(self.gemini_url, json=data)
            response.raise_for_status()
            result = response.json()

            return result["candidates"][0]["content"]["parts"][0]["text"]

        except Exception as e:
            return f"[Gemini Error] {str(e)}"

    # -------------------------
    # 7. GitHub PR 코멘트 작성
    # -------------------------
    def create_review_comment(self, body: str):
        url = f"{self.github_api_base}/repos/{self.repo}/issues/{self.pr_number}/comments"

        headers = {
            "Authorization": f"Bearer {self.github_token}",
            "Accept": "application/vnd.github+json"
        }

        data = {
            "body": body
        }

        requests.post(url, headers=headers, json=data)

    # -------------------------
    # 8. 메인 실행
    # -------------------------
    def run(self):
        files = self.get_pr_files()

        filtered = []

        # 1차 필터
        for f in files:
            category = self.classify_file(f["filename"])

            if category == "ignore":
                continue

            if f.get("additions", 0) + f.get("deletions", 0) < 5:
                continue

            filtered.append((category, f))

        # 우선순위 정렬
        filtered.sort(key=lambda x: self.priority.get(x[0], 999))

        all_reviews = []

        # 리뷰 실행
        for category, file in filtered:
            diff = self.extract_meaningful_diff(file.get("patch", ""))

            if not diff.strip():
                continue

            chunks = self.split_chunks(diff)

            for chunk in chunks:
                prompt = self.build_prompt(chunk, category)

                if not prompt:
                    continue

                review = self.call_gemini(prompt)

                all_reviews.append(f"### 📄 {file['filename']}\n{review}")

        # 결과 코멘트 작성
        if all_reviews:
            final_comment = "\n\n".join(all_reviews)
            self.create_review_comment(final_comment)


# -------------------------
# 실행
# -------------------------
if __name__ == "__main__":
    reviewer = GeminiCodeReview()
    reviewer.run()