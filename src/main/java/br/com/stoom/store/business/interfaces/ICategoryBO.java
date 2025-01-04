package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.category.CategoryRequestDTO;
import br.com.stoom.store.dto.category.CategoryResponseDTO;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import org.springframework.data.domain.Page;

public interface ICategoryBO {

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    Category findByName(String name) throws ServiceException;

    Page<CategoryResponseDTO> findAllCategories(Integer page, Integer size);

    CategoryResponseDTO changeStatus(Long id, Status status) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
