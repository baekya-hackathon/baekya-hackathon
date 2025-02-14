package baekya.backend.domain.api.controller;

import baekya.backend.domain.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController()
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/perplexity/{category}")
    public ResponseEntity<?> getPerplexityResponse(@PathVariable String category) {
            String prompt = apiService.generatePrompt(category);
            String response = apiService.queryPerplexity(prompt);

            // 응답 파싱 및 저장
            List<Map<String, Object>> parsedResponse = apiService.parseNewsResponse(response);
            apiService.saveArticles(parsedResponse, category);

        return ResponseEntity.ok(response);
    }

}
