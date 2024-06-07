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

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leituras")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Leitura>>> getAllLeituras(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "valor") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
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
    public ResponseEntity<EntityModel<Leitura>> getLeituraById(@PathVariable Long id) {
        Optional<Leitura> leitura = leituraService.getLeituraById(id);
        return leitura.map(l -> EntityModel.of(l,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<LeituraResponseDto>> createLeitura(@RequestBody Leitura leitura) {
        LeituraResponseDto createdLeitura = leituraService.createLeitura(leitura);
        EntityModel<LeituraResponseDto> entityModel = EntityModel.of(createdLeitura,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(createdLeitura.id())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Leitura>> updateLeitura(@PathVariable Long id, @RequestBody Leitura leituraDetails) {
        Optional<Leitura> leituraOptional = leituraService.updateLeitura(id, leituraDetails);
        return leituraOptional.map(l -> EntityModel.of(l,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).getLeituraById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeitura(@PathVariable Long id) {
        if (leituraService.deleteLeitura(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}