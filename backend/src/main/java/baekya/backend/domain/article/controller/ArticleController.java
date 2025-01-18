package baekya.backend.domain.article.controller;

import baekya.backend.common.response.BaekyaApiResponse;
import baekya.backend.domain.article.dto.response.ArticleResponse;
import baekya.backend.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static baekya.backend.domain.article.message.ArticleMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ArticleController implements ArticleApi {
     private final ArticleService articleService;

     @GetMapping("/category")
     public BaekyaApiResponse<List<ArticleResponse>> getArticleByCategory(@RequestParam("category") String category) {
          List<ArticleResponse> response = articleService.getArticleByCategoryName(category);
          return BaekyaApiResponse.createResponse(response, SUCCESS_GET_ARTICLES);
     }

     @GetMapping("/category/{articleId}")
     public BaekyaApiResponse<ArticleResponse> getArticleDetail(@PathVariable("articleId") Long articleId) {
          ArticleResponse response = articleService.getArticleDetail(articleId);
          return BaekyaApiResponse.createResponse(response, SUCCESS_GET_ARTICLES_DETAIL);
     }
}