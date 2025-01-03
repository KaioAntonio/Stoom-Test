package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandBO implements IBrandBO {

    private final BrandRepository brandRepository;

    public Brand createBrand(String name) {
        Brand brand = brandRepository.findByName(name)
                .orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setName(name);
                    return brandRepository.save(newBrand);
                });
        return brand;
    }
}
