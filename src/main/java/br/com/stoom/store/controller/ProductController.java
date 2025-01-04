package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.config.ServiceException;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import br.com.stoom.store.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size
    ) {
        Page<ProductResponseDTO> p = productService.findAllActiveProducts(page,size);

        return p.hasContent() ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO product) throws ServiceException {
        return new ResponseEntity<>(productService.create(product), CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductCreateDTO product
    ) throws ServiceException {
        return new ResponseEntity<>(productService.update(id, product), OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws ServiceException {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/status/{id}")
    public ResponseEntity<ProductResponseDTO> changeStatus(
            @PathVariable("id") Long id,
            @RequestParam Status status
    )
            throws ServiceException {
        return new ResponseEntity<>(productService.updateStatus(id, status), OK);
    }

}
