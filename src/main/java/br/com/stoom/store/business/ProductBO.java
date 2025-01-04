package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.config.ServiceException;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.model.product.Product;
import br.com.stoom.store.repository.ProductRepository;
import br.com.stoom.store.utils.SkuGenerator;
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
    private final SkuGenerator skuGenerator;
    private final ProductRepository productRepository;

    public Page<ProductResponseDTO> findAllActiveProducts(Integer page, Integer size) {

        Page<Product> activeProducts = repository.findAllByStatus(Status.ATIVO, PageRequest.of(page, size));

        return activeProducts.map(product -> mapper.convertValue(product, ProductResponseDTO.class));
    }

    public ProductResponseDTO create(ProductCreateDTO productRequestDTO) throws ServiceException {
        Category category = categoryBO.findByName(productRequestDTO.getCategory());

        Brand brand = brandBO.findByName(productRequestDTO.getBrand());

        Product product = createProduct(productRequestDTO, brand, category);
        return mapper.convertValue(product, ProductResponseDTO.class);
    }

    public ProductResponseDTO update(Long id, ProductCreateDTO productRequestDTO) throws ServiceException {
        Product product = findById(id);

        Category category = categoryBO.findByName(productRequestDTO.getCategory());
        Brand brand = brandBO.findByName(productRequestDTO.getBrand());

        updateProductDetails(productRequestDTO, product, brand, category);

        productRepository.save(product);
        return mapper.convertValue(product, ProductResponseDTO.class);
    }


    public ProductResponseDTO updateStatus(Long id, Status status) throws ServiceException {
        Product product = findById(id);
        product.setStatus(status);
        productRepository.save(product);
        return mapper.convertValue(product, ProductResponseDTO.class);
    }

    public void delete(Long id) throws ServiceException {
        Product product = findById(id);
        repository.delete(product);
    }

    public Product findById(Long id) throws ServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException("Produto não encontrado"));
    }

    private Product createProduct(ProductCreateDTO productRequestDTO, Brand brand, Category category) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setStatus(Status.ATIVO);
        product.setDescription(productRequestDTO.getDescription());
        product.setSku(skuGenerator.generateSku(category.getName(), brand.getName(), productRequestDTO.getName()));
        product.setQuantity(productRequestDTO.getQuantity());
        product.setBrand(brand);
        product.setCategory(category);
        product = repository.save(product);
        return product;
    }

    private static void updateProductDetails(ProductCreateDTO productRequestDTO, Product product, Brand brand, Category category) {
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setDescription(productRequestDTO.getDescription());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setBrand(brand);
        product.setCategory(category);
    }


}
