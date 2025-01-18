package baekya.backend.domain.article.service;

import baekya.backend.domain.article.dto.response.ArticleResponse;
import baekya.backend.domain.article.entity.Article;
import baekya.backend.domain.article.repository.ArticleRepository;
import baekya.backend.domain.category.entity.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleResponse> getArticleByCategoryName(String categoryName) {
        CategoryType categoryType = CategoryType.from(categoryName);
        List<Article> articles = articleRepository.findArticlesByCategoryName(categoryType);
        return ArticleResponse.fromSummary(articles);
    }

    public ArticleResponse getArticleDetail(Long id) {
        Article article = articleRepository.findArticleById(id);
        return ArticleResponse.fromDetail(article);
    }
}
