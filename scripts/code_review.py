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

    # 1. PR diff 가져오기
    def get_diff(self):
        url = f"https://api.github.com/repos/{self.repo}/pulls/{self.pr_number}"
        headers = {
            "Authorization": f"token {self.github_token}",
            "Accept": "application/vnd.github.v3+json"
        }

        res = requests.get(url, headers=headers)
        if res.status_code != 200:
            raise Exception(f"PR 정보 조회 실패: {res.text}")

        diff_url = res.json()["diff_url"]

        diff_res = requests.get(diff_url)
        if diff_res.status_code != 200:
            raise Exception(f"diff 조회 실패: {diff_res.text}")

        return diff_res.text

    # 2. Gemini API 호출
    def call_gemini(self, diff):
        client = genai.Client(api_key=self.gemini_api_key)

        prompt = f"""
            You are a senior Android developer.
            리뷰는 한국어로 부탁해.
            Review this PR diff:
            
            {diff[:8000]}
            
            Focus on:
            - MVI 구조
            - Coroutine / Flow misuse
            - 메모리 누수
            - 성능 문제
            - 코드 가독성
            
            Return:
            1. 🔴 Critical Issues
            2. 🟡 Improvements
            3. ✅ Good Points
        """

        response = client.models.generate_content(
            model="gemini-3-flash-preview", contents=prompt
        )

        return getattr(response, "text", "❌ AI 응답 없음")

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

        diff = self.get_diff()
        print("✅ diff 가져오기 완료")

        review = self.call_gemini(diff)
        print("✅ AI 리뷰 생성 완료")

        self.comment_pr(review)
        print("✅ PR 코멘트 등록 완료")


if __name__ == "__main__":
    GeminiCodeReview().run()
