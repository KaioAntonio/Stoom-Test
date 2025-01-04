package br.com.stoom.store.business.helper;

import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.model.product.Product;

import java.math.BigDecimal;

public class ProductHelper {

    public ProductCreateDTO getProductCreateDTO(Brand brand, Category category) {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("Product 1");
        productCreateDTO.setDescription("test product");
        productCreateDTO.setPrice(new BigDecimal("100.00"));
        productCreateDTO.setQuantity(10);
        productCreateDTO.setBrand(brand.getName());
        productCreateDTO.setCategory(category.getName());
        return productCreateDTO;
    }

    public Product getProduct(Category category, Brand brand) {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("test product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStatus(Status.ATIVO);
        product.setCategory(category);
        product.setBrand(brand);
        return product;
    }
}
