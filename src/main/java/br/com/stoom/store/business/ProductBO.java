package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.model.product.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBO implements IProductBO {

    private final ProductRepository repository;
    private final ObjectMapper mapper;
    private final CategoryBO categoryBO;
    private final BrandBO brandBO;

    public Page<ProductResponseDTO> findAllActiveProducts(Integer page, Integer size) {

        Page<Product> activeProducts = repository.findAllByStatus(Status.ATIVO, PageRequest.of(page, size));

        return activeProducts.map(product -> mapper.convertValue(product, ProductResponseDTO.class));
    }

    public ProductResponseDTO create(ProductCreateDTO productRequestDTO) {
        Category category = categoryBO.createCategory(productRequestDTO.getCategory());

        Brand brand = brandBO.createBrand(productRequestDTO.getBrand());

        Product product = createProduct(productRequestDTO, brand, category);
        return mapper.convertValue(product, ProductResponseDTO.class);
    }

    private Product createProduct(ProductCreateDTO productRequestDTO, Brand brand, Category category) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setStatus(Status.ATIVO);
        product.setDescription(productRequestDTO.getDescription());
        product.setSku(productRequestDTO.getSku());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setBrand(brand);
        product.setCategory(category);
        product = repository.save(product);
        return product;
    }


}
