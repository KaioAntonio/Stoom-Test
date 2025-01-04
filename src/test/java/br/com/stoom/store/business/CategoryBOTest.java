package br.com.stoom.store.business;

import br.com.stoom.store.business.helper.CategoryHelper;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.category.CategoryRequestDTO;
import br.com.stoom.store.dto.category.CategoryResponseDTO;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.repository.CategoryRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryBOTest {

    @InjectMocks
    private CategoryBO categoryBO;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryHelper categoryHelper = new CategoryHelper();

    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(categoryBO, "mapper", objectMapper);
    }

    @Test
    public void whenTryFindAllCategoriesReturnSuccess() {
        Category category = categoryHelper.getCategory();

        List<Category> categoryList = Collections.singletonList(category);
        Page<Category> categoryPage = new PageImpl<>(categoryList);

        when(categoryRepository.findAllByStatus(any(),
                any())).thenReturn(categoryPage);

        Page<CategoryResponseDTO> result = categoryBO.findAllCategories(0, 15);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void whenTryFindByNameReturnSuccess() throws ServiceException {
        String name = "Category 1";
        Category category = categoryHelper.getCategory();

        when(categoryRepository.findByName(any())).thenReturn(Optional.of(category));

        Category result = categoryBO.findByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
    }

    @Test
    public void whenTryFindByNameReturnException() {
        when(categoryRepository.findByName(any())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class,
                () -> categoryBO.findByName(anyString()));

        Assertions.assertEquals(exception.getMessage(), "Categoria não encontrada");
    }

    @Test
    public void whenTryCreateCategoryReturnSuccess(){
        Category category = categoryHelper.getCategory();
        CategoryRequestDTO categoryRequestDTO = categoryHelper.getCategoryRequestDTO();
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryResponseDTO result = categoryBO.createCategory(categoryRequestDTO);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    public void whenTryUpdateCategoryReturnSuccess() throws ServiceException {
        Long id = 1L;
        Category category = categoryHelper.getCategory();
        CategoryRequestDTO categoryRequestDTO = categoryHelper.getCategoryRequestDTO();
        categoryRequestDTO.setName("Category 2");

        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryResponseDTO result = categoryBO.upload(id, categoryRequestDTO);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    public void whenTryChangeStatusReturnSuccess() throws ServiceException {
        Long id = 1L;
        Category category = categoryHelper.getCategory();
        Status status = Status.INATIVO;
        category.setStatus(status);

        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryResponseDTO result = categoryBO.changeStatus(id, status);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(status, result.getStatus());
    }

    @Test
    public void whenTryDeleteCategoryReturnSuccess() throws ServiceException {
        Long id = 1L;
        Category category = categoryHelper.getCategory();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        categoryBO.delete(id);

        verify(categoryRepository, times(1)).delete(category);
    }


}
