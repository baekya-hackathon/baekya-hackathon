package baekya.backend.domain.article.entity;

import baekya.backend.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Article")
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    private String title;
    private String keyword;
    private String summary;
    private String newsLink;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Article(String title, String keyword, String summary, String newsLink, Category category) {
        this.title = title;
        this.keyword = keyword;
        this.summary = summary;
        this.newsLink = newsLink;
        this.category = category;
    }
}
