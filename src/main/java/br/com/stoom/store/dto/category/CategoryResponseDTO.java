package br.com.stoom.store.dto.category;

import br.com.stoom.store.model.enums.Status;
import lombok.Data;

@Data
public class CategoryResponseDTO extends CategoryRequestDTO{

    private Long id;
    private Status status;
}
