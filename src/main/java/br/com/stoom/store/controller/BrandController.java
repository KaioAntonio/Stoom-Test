package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.config.ServiceException;
import br.com.stoom.store.dto.brand.BrandRequestDTO;
import br.com.stoom.store.dto.brand.BrandResponseDTO;
import br.com.stoom.store.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/brand/")
@RequiredArgsConstructor
public class BrandController {

    private final BrandBO brandBO;

    @GetMapping(value = "/")
    public ResponseEntity<Page<BrandResponseDTO>> findAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "15") Integer size
    ) {
            Page<BrandResponseDTO> brands = brandBO.findAllBrands(page,size);

            return brands.hasContent() ? ResponseEntity.ok(brands) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<BrandResponseDTO> create(
            @RequestBody @Valid BrandRequestDTO brandRequestDTO
    ) {
        return new ResponseEntity<>(brandBO.createBrand(brandRequestDTO), OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BrandResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid BrandRequestDTO brandRequestDTO
    ) throws ServiceException {
        return new ResponseEntity<>(brandBO.updateBrand(id, brandRequestDTO), OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<BrandResponseDTO> changeStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) throws ServiceException {
        return new ResponseEntity<>(brandBO.changeStatus(id,status), OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) throws ServiceException {
        brandBO.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

}
