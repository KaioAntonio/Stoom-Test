package br.com.stoom.store.business.helper;

import br.com.stoom.store.dto.brand.BrandRequestDTO;
import br.com.stoom.store.model.brand.Brand;

public class BrandHelper {

    public Brand getBrand() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Brand 1");
        return brand;
    }

    public BrandRequestDTO getBrandRequestDTO() {
        BrandRequestDTO brandRequestDTO = new BrandRequestDTO();
        brandRequestDTO.setName("Brand 1");
        return brandRequestDTO;
    }
}
