package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.dtos.leitura.LeituraResponseDto;
import br.com.bluesense.backendjava.entities.Leitura;
import br.com.bluesense.backendjava.services.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leituras")
@Api(tags = "Leituras")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    @GetMapping
    @ApiOperation(value = "Obter todas as leituras", response = PagedModel.class)
    public ResponseEntity<PagedModel<EntityModel<Leitura>>> getAllLeituras(
            @ApiParam(value = "Número da página", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Tamanho da página", defaultValue = "3") @RequestParam(defaultValue = "3") int size,
            @ApiParam(value = "Campo para ordenação", defaultValue = "valor") @RequestParam(defaultValue = "valor") String sortBy,
            @ApiParam(value = "Ordem de classificação (asc ou desc)", defaultValue = "asc") @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<Leitura> leituras = leituraService.getAllLeituras(page, size, sortBy, sortOrder);
        PageMetadata pageMetadata = new PageMetadata(size, page, leituras.getTotalElements());
        PagedModel<EntityModel<Leitura>> pagedModel = PagedModel.of(
                leituras.getContent().stream()
                        .map(leitura -> EntityModel.of(leitura,
                                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(leitura.getId())).withSelfRel()))
                        .collect(Collectors.toList()),
                pageMetadata
        );

        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class)
                .getAllLeituras(page, size, sortBy, sortOrder)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obter uma leitura pelo ID", response = EntityModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Leitura encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Leitura não encontrada")
    })
    public ResponseEntity<EntityModel<Leitura>> getLeituraById(@ApiParam(value = "ID da leitura") @PathVariable Long id) {
        Optional<Leitura> leitura = leituraService.getLeituraById(id);
        return leitura.map(l -> EntityModel.of(l,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar uma nova leitura", response = EntityModel.class)
    public ResponseEntity<EntityModel<LeituraResponseDto>> createLeitura(@ApiParam(value = "Detalhes da nova leitura a ser criada") @RequestBody Leitura leitura) {
        LeituraResponseDto createdLeitura = leituraService.createLeitura(leitura);
        EntityModel<LeituraResponseDto> entityModel = EntityModel.of(createdLeitura,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(createdLeitura.id())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma leitura existente", response = EntityModel.class)
    public ResponseEntity<EntityModel<Leitura>> updateLeitura(@ApiParam(value = "ID da leitura a ser atualizada") @PathVariable Long id, 
                                                              @ApiParam(value = "Detalhes da leitura atualizada") @RequestBody Leitura leituraDetails) {
        Optional<Leitura> leituraOptional = leituraService.updateLeitura(id, leituraDetails);
        return leituraOptional.map(l -> EntityModel.of(l,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir uma leitura pelo ID")
    public ResponseEntity<Void> deleteLeitura(@ApiParam(value = "ID da leitura a ser excluída") @PathVariable Long id) {
        if (leituraService.deleteLeitura(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
