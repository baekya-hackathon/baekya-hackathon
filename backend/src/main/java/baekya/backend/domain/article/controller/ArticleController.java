package baekya.backend.domain.article.controller;

import baekya.backend.domain.article.dto.response.ArticleResponse;
import baekya.backend.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ArticleController implements ArticleApi {
    private final ArticleService articleService
