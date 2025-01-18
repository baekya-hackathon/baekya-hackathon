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
    private Long id;
    private String title;
    private String keyword;
    private String summary;
    private String newsLink;

    public static List<ArticleResponse> fromSummary(List<Article> articles) {
        return articles.stream()
                .map(article -> ArticleResponse.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .keyword(article.getKeyword())
                        .build())
                .collect(Collectors.toList());
    }

    // 상세 정보 조회
    public static ArticleResponse fromDetail(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .keyword(article.getKeyword())
                .summary(article.getSummary())
                .newsLink(article.getNewsLink())
                .build();
    }
}
