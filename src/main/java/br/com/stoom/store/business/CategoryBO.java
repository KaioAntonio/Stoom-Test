package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBO implements ICategoryBO {

    private final CategoryRepository categoryRepository;

    public Category createCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(name);
                    return categoryRepository.save(newCategory);
                });
        return category;
    }
}
