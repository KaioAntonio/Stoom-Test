package br.com.stoom.store.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryRequestDTO {

    @Schema(description = "Categoria do produto", example = "Eletrônicos")
    @NotNull(message = "A categoria é obrigatória")
    @Size(min = 1, message = "A categoria não pode estar vazia")
    private String name;
}
