package baekya.backend.domain.category.service;

import baekya.backend.domain.category.entity.Category;
import baekya.backend.domain.category.entity.CategoryType;
import baekya.backend.domain.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // 카테고리가 존재하지 않으면 enum 기반으로 카테고리 추가
        for (CategoryType categoryType : CategoryType.values()) {
            if (categoryRepository.findByName(categoryType).isEmpty()) {
                Category category = Category.builder()
                        .name(categoryType) // enum 값을 기반으로 설정
                        .build();
                categoryRepository.save(category);
            }
        }
    }

}