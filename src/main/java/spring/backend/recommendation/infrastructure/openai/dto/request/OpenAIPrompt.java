package spring.backend.recommendation.infrastructure.openai.dto.request;

public class OpenAIPrompt {

    public static final String DEFAULT_SYSTEM_PROMPT = """
        자투리 시간을 잘 보낼 수 있는 활동을 아래 상황에 맞춰서 구체적으로 추천해줘.
        '자기개발'이란 '시사상식, 지식, 교양 측면의 활동들, 지식과 능력을 확장하고 개인의 성장과 발전을 위한 활동'를 말해.
        사용할 수 있는 플랫폼도 같이 알려주면 좋을 거 같아.
        예시 답변은
        제목 : 최근 흥행작 누구보다 빠르게 찾아보기!
        내용 : 메가박스에서 영화 '대도시의 사랑법' 관람하기
        플랫폼 : 메가박스
        이거처럼 작성해주고, 제목은 예시 답변처럼 앞에 형용하는 멋진 문장으로 써줘
    """;
}
