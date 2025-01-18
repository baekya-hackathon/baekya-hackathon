package baekya.backend.domain.article.dto.response;

import baekya.backend.domain.article.entity.Article;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleResponse {
    private String title;
    private String keyword;

    public static List<ArticleResponse> from(List<Article> articles) {
        return articles.stream()
                .map(article -> ArticleResponse.builder()
                        .title(article.getTitle())
                        .keyword(article.getKeyword())
                        .build())
                .collect(Collectors.toList());
    }
}
