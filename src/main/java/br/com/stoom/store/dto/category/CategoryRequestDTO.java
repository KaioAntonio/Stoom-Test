package br.com.stoom.store.dto.category;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryRequestDTO {

    @NotNull(message = "A categoria é obrigatória")
    @Size(min = 1, message = "A categoria não pode estar vazia")
    private String name;
}
