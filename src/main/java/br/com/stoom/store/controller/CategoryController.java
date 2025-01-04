package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.category.CategoryRequestDTO;
import br.com.stoom.store.dto.category.CategoryResponseDTO;
import br.com.stoom.store.model.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Obter todas as categorias",
            description = "Recupera uma lista paginada de todas as categorias ativas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categorias encontradas"),
                    @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada")
            }
    )
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size
    ) {
        Page<CategoryResponseDTO> categories = categoryBO.findAllCategories(page,size);

        return categories.hasContent() ? ResponseEntity.ok(categories) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    @Operation(
            summary = "Criar uma nova categoria",
            description = "Cria uma nova categoria com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) {
        return new ResponseEntity<>(categoryBO.createCategory(categoryRequestDTO), OK);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Atualizar uma categoria existente",
            description = "Atualiza as informações de uma categoria existente baseado no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) throws ServiceException {
        return new ResponseEntity<>(categoryBO.upload(id, categoryRequestDTO), OK);
    }

    @PatchMapping(value = "/status/{id}")
    @Operation(
            summary = "Alterar o status de uma categoria",
            description = "Atualiza o status de uma categoria (por exemplo, ativa ou inativa) com base no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status da categoria alterado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    public ResponseEntity<CategoryResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) throws ServiceException {
        return new ResponseEntity<>(categoryBO.changeStatus(id,status), OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Excluir uma categoria",
            description = "Exclui uma categoria com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) throws ServiceException {
        categoryBO.delete(id);
        return ResponseEntity.noContent().build();
    }


}
