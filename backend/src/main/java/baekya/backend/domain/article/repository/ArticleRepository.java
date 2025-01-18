package baekya.backend.domain.article.repository;

import baekya.backend.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.category.name = :categoryName")
    List<Article> findArticlesByCategoryName(@Param("categoryName") String categoryName);
}
