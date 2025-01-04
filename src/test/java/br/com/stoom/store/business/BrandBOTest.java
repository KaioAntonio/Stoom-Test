package br.com.stoom.store.business;

import br.com.stoom.store.business.helper.BrandHelper;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.brand.BrandRequestDTO;
import br.com.stoom.store.dto.brand.BrandResponseDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.repository.BrandRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandBOTest {

    @InjectMocks
    private BrandBO brandBO;

    @Mock
    private BrandRepository brandRepository;

    private BrandHelper brandHelper = new BrandHelper();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(brandBO, "objectMapper", objectMapper);
    }

    @Test
    public void whenTryFindAllBrandsReturnSuccess() {
        Brand brand = brandHelper.getBrand();

        List<Brand> brands = Collections.singletonList(brand);
        Page<Brand> brandPage = new PageImpl<>(brands);

        when(brandRepository.findAllByStatus(any(), any())).thenReturn(brandPage);

        Page<BrandResponseDTO> result = brandBO.findAllBrands(0, 15);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void whenTryFindByNameReturnSuccess() throws ServiceException {
        Brand brand = brandHelper.getBrand();
        String name = "Brand 1";

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(brand));

        Brand result = brandBO.findByName(name);

        assertNotNull(result);
        assertEquals(brand, result);
        assertEquals(name, result.getName());
    }

    @Test
    public void whenTryFindByNameReturnException() {
        when(brandRepository.findByName(any())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class,
                () -> brandBO.findByName(anyString()));

        Assertions.assertEquals(exception.getMessage(), "Marca não encontrada");
    }



    @Test
    public void whenTryCreateBrandReturnSuccess() {
        Brand brand = brandHelper.getBrand();

        when(brandRepository.save(any())).thenReturn(brand);

        BrandResponseDTO result = brandBO.createBrand(brandHelper.getBrandRequestDTO());

        assertNotNull(result);
        assertEquals(brand.getId(), result.getId());
        assertEquals(brand.getName(), result.getName());
    }

    @Test
    public void whenTryUpdateBrandReturnSuccess() throws ServiceException {
        Brand brand = brandHelper.getBrand();
        BrandRequestDTO brandRequestDTO = brandHelper.getBrandRequestDTO();
        Long id = 1L;


        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO result = brandBO.updateBrand(id, brandRequestDTO);

        assertNotNull(result);
        assertEquals(brand.getId(), result.getId());
        assertEquals(brand.getName(), result.getName());
    }

    @Test
    public void whenTryChangeStatusReturnSuccess() throws ServiceException {
        Brand brand = brandHelper.getBrand();
        Status status = Status.INATIVO;

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO result = brandBO.changeStatus(brand.getId(), status);

        assertNotNull(result);
        assertEquals(brand.getId(), result.getId());
        assertEquals(brand.getStatus(), result.getStatus());
    }

    @Test
    public void whenTryDeleteBrandReturnSuccess() throws ServiceException {
        Brand brand = brandHelper.getBrand();
        Long id = 1L;

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        brandBO.deleteBrand(id);

        verify(brandRepository, times(1)).delete(brand);
    }
}
