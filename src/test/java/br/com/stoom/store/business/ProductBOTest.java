package br.com.stoom.store.business;

import br.com.stoom.store.business.helper.BrandHelper;
import br.com.stoom.store.business.helper.CategoryHelper;
import br.com.stoom.store.business.helper.ProductHelper;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.model.product.Product;
import br.com.stoom.store.repository.ProductRepository;
import br.com.stoom.store.utils.SkuGenerator;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductBOTest {

    @InjectMocks
    private ProductBO productBO;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandBO brandBO;
    @Mock
    private CategoryBO categoryBO;
    @Mock
    private SkuGenerator skuGenerator;

    private BrandHelper brandHelper = new BrandHelper();
    private ProductHelper productHelper = new ProductHelper();
    private CategoryHelper categoryHelper = new CategoryHelper();
    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(productBO, "mapper", objectMapper);
    }

    @Test
    public void whenTryFindAllProductsReturnSuccess() {
        Category category = categoryHelper.getCategory();

        Brand brand = brandHelper.getBrand();

        Product product = productHelper.getProduct(category, brand);

        List<Product> productList = Collections.singletonList(product);
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findAllByStatus(any(),
                any())).thenReturn(productPage);

        Page<ProductResponseDTO> result = productBO.findAllActiveProducts(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(Long.valueOf(1L), result.getContent().get(0).getId());
        assertEquals("Product 1",  result.getContent().get(0).getName());
        assertEquals("test product",  result.getContent().get(0).getDescription());
        assertEquals(new BigDecimal("100.00"),  result.getContent().get(0).getPrice());
        assertEquals(Status.ATIVO,  result.getContent().get(0).getStatus());
        assertEquals("Category 1",  result.getContent().get(0).getCategory().getName());
        assertEquals("Brand 1",  result.getContent().get(0).getBrand().getName());
    }

    @Test
    public void whenTryCreateProductsReturnSuccess() throws ServiceException {
        Category category = categoryHelper.getCategory();
        Brand brand = brandHelper.getBrand();
        Product product = productHelper.getProduct(category, brand);
        ProductCreateDTO productCreateDTO = productHelper.getProductCreateDTO(brand, category);
        String sku = "Cat-Bra-Pro-" + System.currentTimeMillis();

        when(categoryBO.findByName(anyString())).thenReturn(category);
        when(brandBO.findByName(anyString())).thenReturn(brand);
        when(productRepository.save(any())).thenReturn(product);
        when(skuGenerator.generateSku(category.getName(), brand.getName(), productCreateDTO.getName())).thenReturn(sku);

        ProductResponseDTO productResponseDTO = productBO.create(productCreateDTO);

        assertNotNull(productResponseDTO);
        assertEquals(productCreateDTO.getName(), productResponseDTO.getName());
        assertEquals(productCreateDTO.getDescription(), productResponseDTO.getDescription());
        assertEquals(productCreateDTO.getPrice(), productResponseDTO.getPrice());
    }

    @Test
    public void whenTryCreateProductsWithBrandOrCategoryInativeReturnException() throws ServiceException {
        Category category = categoryHelper.getCategory();
        Brand brand = brandHelper.getBrand();
        category.setStatus(Status.INATIVO);
        brand.setStatus(Status.INATIVO);
        ProductCreateDTO productCreateDTO = productHelper.getProductCreateDTO(brand, category);

        when(categoryBO.findByName(anyString())).thenReturn(category);
        when(brandBO.findByName(anyString())).thenReturn(brand);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> productBO.create(productCreateDTO));

        Assertions.assertEquals(exception.getMessage(), "Categoria ou Marca precisa está ativa");
    }

    @Test
    public void whenTryUpdateProductsReturnSuccess() throws ServiceException {
        Long id = 1L;
        Category category = categoryHelper.getCategory();
        Brand brand = brandHelper.getBrand();
        Product product = productHelper.getProduct(category, brand);
        ProductCreateDTO productCreateDTO = productHelper.getProductCreateDTO(brand,category);
        productCreateDTO.setName("Product 2");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(categoryBO.findByName(anyString())).thenReturn(category);
        when(brandBO.findByName(anyString())).thenReturn(brand);
        when(productRepository.save(any())).thenReturn(product);

        ProductResponseDTO result = productBO.update(id, productCreateDTO);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
    }

    @Test
    public void whenTryDeleteProductsReturnSuccess() throws ServiceException {
        Long id = 1L;
        Category category = categoryHelper.getCategory();
        Brand brand = brandHelper.getBrand();
        Product product = productHelper.getProduct(category, brand);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productBO.delete(id);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void whenTryUpdateStatusReturnSuccess() throws ServiceException {
        Long id = 1L;
        Category category = categoryHelper.getCategory();
        Brand brand = brandHelper.getBrand();
        Product product = productHelper.getProduct(category, brand);
        Status status = Status.INATIVO;

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        ProductResponseDTO result = productBO.updateStatus(id, status);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(Status.INATIVO, result.getStatus());
    }


}
