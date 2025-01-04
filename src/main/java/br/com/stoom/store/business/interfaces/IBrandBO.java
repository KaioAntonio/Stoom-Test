package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.config.ServiceException;
import br.com.stoom.store.dto.brand.BrandRequestDTO;
import br.com.stoom.store.dto.brand.BrandResponseDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.enums.Status;
import org.springframework.data.domain.Page;

public interface IBrandBO {

    BrandResponseDTO createBrand(BrandRequestDTO brandRequestDTO);
    Page<BrandResponseDTO> findAllBrands(Integer page, Integer size);
    BrandResponseDTO updateBrand(Long id, BrandRequestDTO brandRequestDTO) throws ServiceException;
    BrandResponseDTO changeStatus(Long id, Status status) throws ServiceException;
    void deleteBrand(Long id) throws ServiceException;
    Brand findByName(String name) throws ServiceException;
}
