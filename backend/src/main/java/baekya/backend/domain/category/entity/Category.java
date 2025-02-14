package baekya.backend.domain.category.entity;

import baekya.backend.domain.article.entity.Article;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType name;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;

    @Builder
    public Category(CategoryType name) {
        this.name = name;
    }
}
