package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size
    ) {
        Page<ProductResponseDTO> p = productService.findAllActiveProducts(page,size);

        return p.hasContent() ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO product) {
        return new ResponseEntity<>(productService.create(product), CREATED);
    }

}
