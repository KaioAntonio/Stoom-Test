package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.category.CategoryRequestDTO;
import br.com.stoom.store.dto.category.CategoryResponseDTO;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBO implements ICategoryBO {

    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper;

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.save(mapper.convertValue(categoryRequestDTO, Category.class));
        return mapper.convertValue(category, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO upload(Long id, CategoryRequestDTO categoryRequestDTO) throws ServiceException {
        Category category = findById(id);
        category.setName(categoryRequestDTO.getName());
        categoryRepository.save(category);
        return mapper.convertValue(category, CategoryResponseDTO.class);
    }

    public Category findByName(String name) throws ServiceException {
        return categoryRepository.findByName(name).orElseThrow(
                () -> new ServiceException("Categoria não encontrada")
        );
    }

    public Page<CategoryResponseDTO> findAllCategories(Integer page, Integer size) {
        Page<Category> categories = categoryRepository.findAllByStatus(Status.ATIVO, PageRequest.of(page,size));

        return categories.map(category -> mapper.convertValue(category, CategoryResponseDTO.class));
    }

    public CategoryResponseDTO changeStatus(Long id, Status status) throws ServiceException {
        Category category = findById(id);
        category.setStatus(status);
        categoryRepository.save(category);
        return mapper.convertValue(category, CategoryResponseDTO.class);
    }


    public void delete(Long id) throws ServiceException {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    private Category findById(Long id) throws ServiceException {
        return categoryRepository.findById(id).orElseThrow(() -> new ServiceException("Categoria não Encontrada."));
    }
}
