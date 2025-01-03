package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.category.Category;

public interface ICategoryBO {

    Category createCategory(String name);
}
