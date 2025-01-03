package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import org.springframework.data.domain.Page;

public interface IProductBO {

    Page<ProductResponseDTO> findAllActiveProducts(Integer page, Integer size);

    ProductResponseDTO create(ProductCreateDTO productRequestDTO);
}
