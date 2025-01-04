package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.config.exception.ServiceException;
import br.com.stoom.store.dto.brand.BrandRequestDTO;
import br.com.stoom.store.dto.brand.BrandResponseDTO;
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
@RequestMapping("/api/brand/")
@RequiredArgsConstructor
public class BrandController {

    private final BrandBO brandBO;

    @GetMapping(value = "/")
    @Operation(
            summary = "Obter todas as marcas",
            description = "Recupera uma lista paginada de todas as marcas ativas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marcas encontradas"),
                    @ApiResponse(responseCode = "404", description = "Nenhuma marca encontrada")
            }
    )
    public ResponseEntity<Page<BrandResponseDTO>> findAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "15") Integer size
    ) {
            Page<BrandResponseDTO> brands = brandBO.findAllBrands(page,size);

            return brands.hasContent() ? ResponseEntity.ok(brands) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    @Operation(
            summary = "Criar uma nova marca",
            description = "Cria uma nova marca com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Marca criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    public ResponseEntity<BrandResponseDTO> create(
            @RequestBody @Valid BrandRequestDTO brandRequestDTO
    ) {
        return new ResponseEntity<>(brandBO.createBrand(brandRequestDTO), OK);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Atualizar uma marca existente",
            description = "Atualiza as informações de uma marca existente baseado no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Marca não encontrada")
            }
    )
    public ResponseEntity<BrandResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid BrandRequestDTO brandRequestDTO
    ) throws ServiceException {
        return new ResponseEntity<>(brandBO.updateBrand(id, brandRequestDTO), OK);
    }

    @PatchMapping(value = "/{id}")
    @Operation(
            summary = "Alterar o status de uma marca",
            description = "Atualiza o status de uma marca (por exemplo, ativa ou inativa) com base no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status da marca alterado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Marca não encontrada")
            }
    )
    public ResponseEntity<BrandResponseDTO> changeStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) throws ServiceException {
        return new ResponseEntity<>(brandBO.changeStatus(id,status), OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Excluir uma marca",
            description = "Exclui uma marca com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Marca excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Marca não encontrada")
            }
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) throws ServiceException {
        brandBO.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

}
