package br.com.stoom.store.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProductCreateDTO {

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 1, message = "O nome não pode estar vazio")
    private String name;

    @NotNull(message = "A descrição é obrigatória")
    @Size(min = 1, message = "A descrição não pode estar vazia")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
    private BigDecimal price;

    @NotNull(message = "A quantidade é obrigatório")
    private Integer quantity;

    @NotNull(message = "A categoria é obrigatória")
    @Size(min = 1, message = "A categoria não pode estar vazia")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String category;

    @NotNull(message = "A marca é obrigatória")
    @Size(min = 1, message = "A marca não pode estar vazia")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String brand;

}
