package br.com.stoom.store.dto.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BrandRequestDTO {

    @Schema(description = "Marca do produto)", example = "Dell")
    @NotNull(message = "O nome da marca é obrigatória")
    @Size(min = 1, message = "O nome da marca não pode estar vazia")
    private String name;
}
