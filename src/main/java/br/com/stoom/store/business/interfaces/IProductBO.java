package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import br.com.stoom.store.model.enums.Status;
import org.springframework.data.domain.Page;

public interface IProductBO {

    Page<ProductResponseDTO> findAllActiveProducts(Integer page, Integer size);

    ProductResponseDTO create(ProductCreateDTO productRequestDTO) throws ServiceException;

    ProductResponseDTO update(Long id, ProductCreateDTO productRequestDTO) throws ServiceException;

    ProductResponseDTO updateStatus(Long id, Status status) throws ServiceException;

    void delete(Long id) throws ServiceException;

}
