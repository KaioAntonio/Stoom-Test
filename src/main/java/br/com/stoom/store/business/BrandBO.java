package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.brand.BrandRequestDTO;
import br.com.stoom.store.dto.brand.BrandResponseDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.repository.BrandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandBO implements IBrandBO {

    private final BrandRepository brandRepository;
    private final ObjectMapper objectMapper;

    public BrandResponseDTO createBrand(BrandRequestDTO brandRequestDTO) {
        Brand brand =  brandRepository.save(objectMapper.convertValue(brandRequestDTO, Brand.class));
        return objectMapper.convertValue(brand, BrandResponseDTO.class);
    }

    public Page<BrandResponseDTO> findAllBrands(Integer page, Integer size) {
        Page<Brand> brands = brandRepository.findAllByStatus(Status.ATIVO, PageRequest.of(page,size));

        return brands.map(brand -> objectMapper.convertValue(brand, BrandResponseDTO.class));
    }

    public BrandResponseDTO updateBrand(Long id, BrandRequestDTO brandRequestDTO) throws ServiceException {
        Brand brand = findById(id);
        brand.setName(brandRequestDTO.getName());
        brandRepository.save(brand);
        return objectMapper.convertValue(brand, BrandResponseDTO.class);
    }

    public BrandResponseDTO changeStatus(Long id, Status status) throws ServiceException {
        Brand brand = findById(id);
        brand.setStatus(status);
        brandRepository.save(brand);
        return objectMapper.convertValue(brand, BrandResponseDTO.class);
    }

    public void deleteBrand(Long id) throws ServiceException {
        Brand brand = findById(id);
        brandRepository.delete(brand);
    }

    public Brand findByName(String name) throws ServiceException {
        return brandRepository.findByName(name).orElseThrow(
                () -> new ServiceException("Marca não encontrada")
        );
    }

    private Brand findById(Long id) throws ServiceException {
        return brandRepository.findById(id).orElseThrow(() -> new ServiceException("Marca não encontrada."));
    }
}
