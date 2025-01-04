package br.com.stoom.store.business.helper;

import br.com.stoom.store.dto.category.CategoryRequestDTO;
import br.com.stoom.store.model.category.Category;

public class CategoryHelper {

    public Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        return category;
    }

    public CategoryRequestDTO getCategoryRequestDTO() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Category 1");
        return categoryRequestDTO;
    }
}
