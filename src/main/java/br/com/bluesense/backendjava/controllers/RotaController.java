package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.entities.Rota;
import br.com.bluesense.backendjava.services.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Rota>>> getAllRotas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<Rota> rotas = rotaService.getAllRotas(page, size, sortBy, sortOrder);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, rotas.getTotalElements());
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
    public ResponseEntity<EntityModel<Rota>> getRotaById(@PathVariable Long id) {
        Optional<Rota> rota = rotaService.getRotaById(id);
        return rota.map(r -> EntityModel.of(r,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Rota>> createRota(@RequestBody Rota rota) {
        Rota createdRota = rotaService.createRota(rota);
        EntityModel<Rota> entityModel = EntityModel.of(createdRota,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(createdRota.getId())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Rota>> updateRota(@PathVariable Long id, @RequestBody Rota rotaDetails) {
        Optional<Rota> rotaOptional = rotaService.updateRota(id, rotaDetails);
        return rotaOptional.map(r -> EntityModel.of(r,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RotaController.class).getRotaById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRota(@PathVariable Long id) {
        if (rotaService.deleteRota(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}