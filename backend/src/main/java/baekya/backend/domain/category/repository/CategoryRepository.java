package baekya.backend.domain.category.repository;

import baekya.backend.domain.category.entity.Category;
import baekya.backend.domain.category.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryType categoryType);
}
