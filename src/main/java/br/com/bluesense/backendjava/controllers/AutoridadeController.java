package br.com.bluesense.backendjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bluesense.backendjava.entities.Autoridade;
import br.com.bluesense.backendjava.services.AutoridadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autoridades")
@Api(tags = "Autoridades")
public class AutoridadeController {

    @Autowired
    private AutoridadeService autoridadeService;

    @GetMapping
    @ApiOperation(value = "Obter todas as autoridades", response = PagedModel.class)
    public ResponseEntity<PagedModel<EntityModel<Autoridade>>> getAllAutoridades(
            @ApiParam(value = "Número da página", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Tamanho da página", defaultValue = "3") @RequestParam(defaultValue = "3") int size,
            @ApiParam(value = "Campo para ordenação", defaultValue = "nome") @RequestParam(defaultValue = "nome") String sortBy,
            @ApiParam(value = "Ordem de classificação (asc ou desc)", defaultValue = "asc") @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<Autoridade> autoridades = autoridadeService.getAllAutoridades(page, size, sortBy, sortOrder);
        PageMetadata pageMetadata = new PageMetadata(size, page, autoridades.getTotalElements());
        PagedModel<EntityModel<Autoridade>> pagedModel = PagedModel.of(
                autoridades.getContent().stream()
                        .map(autoridade -> EntityModel.of(autoridade,
                                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(autoridade.getId())).withSelfRel()))
                        .collect(Collectors.toList()),
                pageMetadata
        );

        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class)
                .getAllAutoridades(page, size, sortBy, sortOrder)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obter uma autoridade pelo ID", response = EntityModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Autoridade encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Autoridade não encontrada")
    })
    public ResponseEntity<EntityModel<Autoridade>> getAutoridadeById(@ApiParam(value = "ID da autoridade") @PathVariable Long id) {
        Optional<Autoridade> autoridade = autoridadeService.getAutoridadeById(id);
        return autoridade.map(a -> EntityModel.of(a,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar uma nova autoridade", response = EntityModel.class)
    public ResponseEntity<EntityModel<Autoridade>> createAutoridade(@ApiParam(value = "Detalhes da nova autoridade a ser criada") @RequestBody Autoridade autoridade) {
        Autoridade createdAutoridade = autoridadeService.createAutoridade(autoridade);
        EntityModel<Autoridade> entityModel = EntityModel.of(createdAutoridade,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(createdAutoridade.getId())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma autoridade existente", response = EntityModel.class)
    public ResponseEntity<EntityModel<Autoridade>> updateAutoridade(@ApiParam(value = "ID da autoridade a ser atualizada") @PathVariable Long id, 
                                                                     @ApiParam(value = "Detalhes da autoridade atualizada") @RequestBody Autoridade autoridadeDetails) {
        Optional<Autoridade> autoridadeOptional = autoridadeService.updateAutoridade(id, autoridadeDetails);
        return autoridadeOptional.map(a -> EntityModel.of(a,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir uma autoridade pelo ID")
    public ResponseEntity<Void> deleteAutoridade(@ApiParam(value = "ID da autoridade a ser excluída") @PathVariable Long id) {
        if (autoridadeService.deleteAutoridade(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
