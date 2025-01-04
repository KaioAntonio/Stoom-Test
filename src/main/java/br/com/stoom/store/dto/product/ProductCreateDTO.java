package br.com.stoom.store.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProductCreateDTO {

    @Schema(description = "Nome do produto", example = "Notebook")
    @NotNull(message = "O nome é obrigatório")
    @Size(min = 1, message = "O nome não pode estar vazio")
    private String name;

    @Schema(description = "Descrição do produto", example = "Notebook Dell com 16GB RAM")
    @NotNull(message = "A descrição é obrigatória")
    @Size(min = 1, message = "A descrição não pode estar vazia")
    private String description;

    @Schema(description = "Preço do produto", example = "2500.00")
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque do produto", example = "10")
    @NotNull(message = "A quantidade é obrigatório")
    private Integer quantity;

    @Schema(description = "Categoria do produto", example = "Eletrônicos")
    @NotNull(message = "A categoria é obrigatória")
    @Size(min = 1, message = "A categoria não pode estar vazia")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String category;

    @Schema(description = "Marca do produto)", example = "Dell")
    @NotNull(message = "A marca é obrigatória")
    @Size(min = 1, message = "A marca não pode estar vazia")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String brand;

}
