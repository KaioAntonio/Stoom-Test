package br.com.stoom.store.dto.brand;

import br.com.stoom.store.model.enums.Status;
import lombok.Data;

@Data
public class BrandResponseDTO extends BrandRequestDTO{

    private Long id;
    private Status status;
}
