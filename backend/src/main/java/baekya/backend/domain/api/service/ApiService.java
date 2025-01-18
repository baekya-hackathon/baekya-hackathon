package baekya.backend.domain.api.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Value("${perplexity.base-url}")
    private String baseUrl;

    @Value("${perplexity.key}")
    private String apiKey;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Perplexity API 프롬프트 생성
     */
    public String generatePrompt(String category) {
        String newBaseUrl = "https://news.naver.com/section/";
        String urlSuffix = switch (category) {
            case "정치" -> "100";
            case "경제" -> "101";
            case "사회" -> "102";
            case "생활/문화" -> "103";
            default -> throw new IllegalArgumentException("Invalid category description: " + category);
        };

        String categoryUrl = newBaseUrl + urlSuffix;

        return String.format(
                "Summarize the latest %s Korea daily news articles from the following website: %s.\n" +
                        "Each summary must include:\n" +
                        "- 해당 뉴스의 제목 (Title)\n" +
                        "- 3–5문장으로 요약된 뉴스 본문에 대한 요약 내용 (Summary)\n" +
                        "- 3–5개의 뉴스 본문의 중요한 키워드 (Keywords)\n" +
                        "- 해당 뉴스 링크 (NewsLink)\n" +
                        "4개의 뉴스를 JSON 배열 형식으로 제공하세요. 각 항목은 다음 키를 포함해야 합니다: 'title', 'summary', 'keywords', 'NewsLink'.",
                category, categoryUrl
        );
    }


    /**
     * Perplexity API 호출
     */
    public String queryPerplexity(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");
        headers.set("accept", "application/json");

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
                return responseBody.path("choices").get(0).path("message").path("content").asText();
            } else {
                throw new RuntimeException("API returned non-200 response: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during Perplexity API call: " + e.getMessage(), e);
        }
    }


    /**
     * 4개의 카테고리별 뉴스 요약 생성
     */
    public Map<String, List<Map<String, String>>> getNewsByCategories() {
        Map<String, String> descriptions = Map.of(
                "SOCIAL", "사회",
                "POLITICS", "정치",
                "ECONOMY", "경제",
                "LIFE", "생활/문화"
        );

        Map<String, List<Map<String, String>>> newsByCategory = new HashMap<>();

        descriptions.forEach((key, description) -> {
            String prompt = generatePrompt(description);
            String response = queryPerplexity(prompt);
            List<Map<String, String>> parsedResponse = parseNewsResponse(response);
            newsByCategory.put(key, parsedResponse);
        });

        return newsByCategory;
    }

    /**
     * Perplexity API 응답 데이터 파싱
     */
    private List<Map<String, String>> parseNewsResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<Map<String, String>> articles = new ArrayList<>();
            for (JsonNode article : rootNode) {
                Map<String, String> articleData = new HashMap<>();
                articleData.put("제목", article.path("title").asText());
                articleData.put("요약", article.path("summary").asText());
                articleData.put("키워드", article.path("keywords").toString());
                articleData.put("뉴스 링크", article.path("NewsLink").asText());
                articles.add(articleData);
            }
            return articles;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse news response: " + e.getMessage(), e);
        }
    }
}
