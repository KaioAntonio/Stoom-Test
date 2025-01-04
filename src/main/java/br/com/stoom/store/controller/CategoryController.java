package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.config.ServiceException;
import br.com.stoom.store.dto.category.CategoryRequestDTO;
import br.com.stoom.store.dto.category.CategoryResponseDTO;
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
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryBO categoryBO;

    @GetMapping(value = "/")
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size
    ) {
        Page<CategoryResponseDTO> categories = categoryBO.findAllCategories(page,size);

        return categories.hasContent() ? ResponseEntity.ok(categories) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) {
        return new ResponseEntity<>(categoryBO.createCategory(categoryRequestDTO), OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) throws ServiceException {
        return new ResponseEntity<>(categoryBO.upload(id, categoryRequestDTO), OK);
    }

    @PatchMapping(value = "/status/{id}")
    public ResponseEntity<CategoryResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) throws ServiceException {
        return new ResponseEntity<>(categoryBO.changeStatus(id,status), OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) throws ServiceException {
        categoryBO.delete(id);
        return ResponseEntity.noContent().build();
    }


}
