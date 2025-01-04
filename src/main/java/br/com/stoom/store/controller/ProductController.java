package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.product.ProductCreateDTO;
import br.com.stoom.store.dto.product.ProductResponseDTO;
import br.com.stoom.store.model.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    @Operation(
            summary = "Obter todos os produtos ativos",
            description = "Recupera uma lista paginada de produtos ativos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
            }
    )
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size
    ) {
        Page<ProductResponseDTO> p = productService.findAllActiveProducts(page,size);

        return p.hasContent() ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    @Operation(
            summary = "Criar um novo produto",
            description = "Cria um novo produto com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO product) throws ServiceException {
        return new ResponseEntity<>(productService.create(product), CREATED);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Atualizar um produto existente",
            description = "Atualiza as informações de um produto existente baseado no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductCreateDTO product
    ) throws ServiceException {
        return new ResponseEntity<>(productService.update(id, product), OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Excluir um produto",
            description = "Exclui um produto com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws ServiceException {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/status/{id}")
    @Operation(
            summary = "Alterar o status de um produto",
            description = "Atualiza o status de um produto (por exemplo, ativo ou inativo) com base no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status do produto alterado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    public ResponseEntity<ProductResponseDTO> changeStatus(
            @PathVariable("id") Long id,
            @RequestParam Status status
    )
            throws ServiceException {
        return new ResponseEntity<>(productService.updateStatus(id, status), OK);
    }

}
