package baekya.backend.domain.article.service;

import baekya.backend.domain.article.dto.response.ArticleResponse;
import baekya.backend.domain.article.entity.Article;
import baekya.backend.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleResponse> getArticleByCategoryName(String categoryName) {
        List<Article> articles = articleRepository.findArticlesByCategoryName(categoryName);
        return ArticleResponse.from(articles);
    }
}
