package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.entities.Rota;
import br.com.bluesense.backendjava.services.RotaService;
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
@RequestMapping("/rotas")
@Api(tags = "Rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @GetMapping
    @ApiOperation(value = "Obter todas as rotas", response = PagedModel.class)
    public ResponseEntity<PagedModel<EntityModel<Rota>>> getAllRotas(
            @ApiParam(value = "Número da página", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Tamanho da página", defaultValue = "3") @RequestParam(defaultValue = "3") int size,
            @ApiParam(value = "Campo para ordenação", defaultValue = "id") @RequestParam(defaultValue = "id") String sortBy,
            @ApiParam(value = "Ordem de classificação (asc ou desc)", defaultValue = "asc") @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<Rota> rotas = rotaService.getAllRotas(page, size, sortBy, sortOrder);
        PageMetadata pageMetadata = new PageMetadata(size, page, rotas.getTotalElements());
        PagedModel<EntityModel<Rota>> pagedModel = PagedModel.of(
                rotas.getContent().stream()
                        .map(rota -> EntityModel.of(rota,
                                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(rota.getId())).withSelfRel()))
                        .collect(Collectors.toList()),
                pageMetadata
        );

        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class)
                .getAllRotas(page, size, sortBy, sortOrder)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obter uma rota pelo ID", response = EntityModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rota encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Rota não encontrada")
    })
    public ResponseEntity<EntityModel<Rota>> getRotaById(@ApiParam(value = "ID da rota") @PathVariable Long id) {
        Optional<Rota> rota = rotaService.getRotaById(id);
        return rota.map(r -> EntityModel.of(r,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar uma nova rota", response = EntityModel.class)
    public ResponseEntity<EntityModel<Rota>> createRota(@ApiParam(value = "Detalhes da nova rota a ser criada") @RequestBody Rota rota) {
        Rota createdRota = rotaService.createRota(rota);
        EntityModel<Rota> entityModel = EntityModel.of(createdRota,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(createdRota.getId())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma rota existente", response = EntityModel.class)
    public ResponseEntity<EntityModel<Rota>> updateRota(@ApiParam(value = "ID da rota a ser atualizada") @PathVariable Long id, 
                                                        @ApiParam(value = "Detalhes da rota atualizada") @RequestBody Rota rotaDetails) {
        Optional<Rota> rotaOptional = rotaService.updateRota(id, rotaDetails);
        return rotaOptional.map(r -> EntityModel.of(r,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir uma rota pelo ID")
    public ResponseEntity<Void> deleteRota(@ApiParam(value = "ID da rota a ser excluída") @PathVariable Long id) {
        if (rotaService.deleteRota(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
