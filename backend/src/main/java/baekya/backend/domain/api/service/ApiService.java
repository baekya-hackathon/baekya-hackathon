package baekya.backend.domain.api.service;



import baekya.backend.domain.article.entity.Article;
import baekya.backend.domain.article.repository.ArticleRepository;
import baekya.backend.domain.article.service.ArticleService;
import baekya.backend.domain.category.entity.Category;
import baekya.backend.domain.category.entity.CategoryType;
import baekya.backend.domain.category.repository.CategoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class ApiService {
        private final RestTemplate restTemplate;
        private final ArticleRepository articleRepository;
        private final CategoryRepository categoryRepository;

        @Value("${perplexity.base-url}")
        private String baseUrl;

        @Value("${perplexity.key}")
        private String apiKey;

        private static final Map<String, String> DESCRIPTIONS = Map.of(
                "social", "사회",
                "politics", "정치",
                "economy", "경제",
                "life", "생활"
        );

        private static final String PROMPT_TEMPLATE = """
    다음 웹사이트: %s 에서 최신 %s 한국 일간 뉴스 기사를 요약하세요.
    정확히 3개의 기사를 엄격한 JSON 배열 형식으로 제공하세요. 각 기사는 다음 필드를 포함해야 합니다.  본문 내부에는 ""쌍따음표,''따음표를 절대 사용하지 않습니다 ):
    - "title": 뉴스 기사의 제목 (문자열).
    - "summary": 기사 내용에 대한 3문장 요약 (문자열).
    - "keywords": 기사에서 중요한 4-5개의 키워드 목록 (문자열 배열).
    - "newsLink": 해당 뉴스의 기사 상세 URL. 링크 클릭시 해당 뉴스로 이동하도록 합니다 (문자열).

    JSON 응답은 다음 형식을 엄격히 따라야 합니다. 각 가사의 필드는 위의 내용을 반드시 포함합니다. 예시가 아닌 실제 데이터 입니다.:
    특수문자의 경우 " ' , ? ! { } ( ) [ ] : / . = & 만 허용합니다.
    본문 내부에는 ""쌍따음표,''따음표를 절대 사용하지 않습니다. :
    
    [
        {
            "title": "예시 제목입니다. 본문 내부에는 쌍따음표("") 따음표('') 를 절대 사용하지 않습니다.",
            "summary": "이것은 예시 요약입니다. 요약 내부에는 내부에는 쌍따음표("") 따음표('') 를 절대 사용하지 않습니다.",
            "keywords": ["키워드1", "키워드2", "키워드3", "키워드4"],
            "newsLink": "해당 뉴스의 기사 상세 URL. 링크 클릭시 해당 뉴스로 이동하도록 합니다 (문자열)."
        },
        {
            "title": "예시 제목입니다. 본문 내부에는 쌍따음표("") 따음표('') 를 절대 사용하지 않습니다.",
            "summary": "이것은 예시 요약입니다. 요약 내부에는 내부에는 쌍따음표("") 따음표('') 를 절대 사용하지 않습니다.",
            "keywords": ["키워드1", "키워드2", "키워드3", "키워드4"],
            "newsLink": "해당 뉴스의 기사 상세 URL. 링크 클릭시 해당 뉴스로 이동하도록 합니다 (문자열)."
        },
        {
            "title": "예시 제목입니다. 본문 내부에는 쌍따음표("") 따음표('') 를 절대 사용하지 않습니다.",
            "summary": "이것은 예시 요약입니다. 요약 내부에는 내부에는 쌍따음표("") 따음표('') 를 절대 사용하지 않습니다.",
            "keywords": ["키워드1", "키워드2", "키워드3", "키워드4"],
            "newsLink": "해당 뉴스의 기사 상세 URL. 링크 클릭시 해당 뉴스로 이동하도록 합니다 (문자열)."
        }
    ]

    JSON 형식이 반드시 유효하도록 하세요. JSON 이외의 추가 텍스트나 설명을 포함하지 마세요.
    """;

        /**
         * Perplexity API 프롬프트 생성
         * 이 메소드는 주어진 카테고리에 대한 다양한 출처에서 뉴스 기사를 가져와, 이를 퍼플렉시티 API에 전달할 프롬프트로 변환합니다.
         */
        public String generatePrompt(String category) {
            // 카테고리에 대한 설명을 가져옵니다.
            String description = DESCRIPTIONS.get(category);
            if (description == null) {
                throw new IllegalArgumentException("Invalid category description: " + category);
            }

            // 카테고리에 대한 뉴스 검색 쿼리 생성
            String query = String.format("한국의 %s 관련 최신 뉴스", description);

            // 해당 쿼리를 사용하여 다양한 뉴스 출처에서 뉴스를 가져오는 프롬프트 생성
            return String.format(PROMPT_TEMPLATE, category, query);
        }

        /**
         * Perplexity API 호출
         */
        public String queryPerplexity(String query) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama-3.1-sonar-small-128k-online");
            List<Map<String, String>> messages = List.of(
                    Map.of("role", "system", "content", "Respond concisely and follow the format. and User is Korean"),
                    Map.of("role", "user", "content", query)
            );
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode responseBody = objectMapper.readTree(response.getBody());
                    JsonNode choicesNode = responseBody.path("choices");

                    if (!choicesNode.isArray() || choicesNode.isEmpty()) {
                        throw new RuntimeException("Invalid response format: Missing 'choices'");
                    }
                    return choicesNode.get(0).path("message").path("content").asText();
                } else {
                    throw new RuntimeException("API returned non-200 response: " + response.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Error during Perplexity API call: {}", e.getMessage(), e);
                throw new RuntimeException("Error during Perplexity API call", e);
            }
        }

        /**
         * 카테고리별 뉴스 요약 생성 및 저장
         */
        public Map<String, List<Map<String, Object>>> getNewsByCategories() {
            Map<String, List<Map<String, Object>>> newsByCategory = new HashMap<>();

            DESCRIPTIONS.forEach((categoryKey, description) -> {
                try {
                    String prompt = generatePrompt(description);
                    String response = queryPerplexity(prompt);
                    List<Map<String, Object>> parsedResponse = parseNewsResponse(response);
                    saveArticles(parsedResponse, description);
                    newsByCategory.put(categoryKey, parsedResponse);
                } catch (Exception e) {
                    log.error("Failed to process category: {}", categoryKey, e);
                }
            });

            return newsByCategory;
        }

        /**
         * Perplexity API 응답 데이터 파싱
         */
        public List<Map<String, Object>> parseNewsResponse(String response) {
            try {
                // JSON 배열 시작 위치 찾기
                int jsonStartIndex = response.indexOf("[");
                if (jsonStartIndex == -1) {
                    throw new RuntimeException("Invalid response format: JSON array not found");
                }

                // JSON 배열만 추출
                String jsonArray = response.substring(jsonStartIndex);

                // ObjectMapper로 JSON 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonArray);

                if (!rootNode.isArray()) {
                    throw new RuntimeException("Expected JSON array but got: " + rootNode.toString());
                }

                // 결과 리스트 생성
                List<Map<String, Object>> articles = new ArrayList<>();
                for (JsonNode articleNode : rootNode) {
                    try {
                        Map<String, Object> articleData = new HashMap<>();
                        articleData.put("title", articleNode.path("title").asText(null));
                        articleData.put("summary", articleNode.path("summary").asText(null));
                        articleData.put("keywords", objectMapper.convertValue(articleNode.path("keywords"), List.class));
                        articleData.put("newsLink", articleNode.path("newsLink").asText(null));
                        articles.add(articleData);
                    }catch (Exception e){
                        log.warn("Failed to parse article: {}", articleNode, e);
                        continue;
                    }
                }
                return articles;
            } catch (Exception e) {
                log.error("Failed to parse response: {}", response, e);
                throw new RuntimeException("Error parsing Perplexity API response", e);
            }
        }

        /**
         * 기사를 DB에 저장
         */
        public void saveArticles(List<Map<String, Object>> articlesData, String categoryDescription) {
            log.info("Saving articles for category: {}", categoryDescription);

            CategoryType categoryType = CategoryType.from(categoryDescription);
            Category category = categoryRepository.findByName(categoryType)
                    .orElseThrow(() -> {
                        log.error("Category not found: {}", categoryDescription);
                        return new RuntimeException("Category not found: " + categoryDescription);
                    });

            articlesData.forEach(articleData -> {
                Article article = Article.builder()
                        .title((String) articleData.get("title"))
                        .summary((String) articleData.get("summary"))
                        .keyword(String.valueOf(articleData.get("keywords")))
                        .newsLink((String) articleData.get("newsLink"))
                        .category(category)
                        .build();
                articleRepository.save(article);
                log.info("Saved article: {}", article.getTitle());
            });

            log.info("All articles saved for category: {}", categoryDescription);
        }

    }
