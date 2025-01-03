package br.com.stoom.store.dto.product;

import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Status status;
    private String sku;
    private Integer quantity;
    private Category category;
    private Brand brand;
}
