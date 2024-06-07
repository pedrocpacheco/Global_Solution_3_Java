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

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autoridades")
public class AutoridadeController {

    @Autowired
    private AutoridadeService autoridadeService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Autoridade>>> getAllAutoridades(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
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
    public ResponseEntity<EntityModel<Autoridade>> getAutoridadeById(@PathVariable Long id) {
        Optional<Autoridade> autoridade = autoridadeService.getAutoridadeById(id);
        return autoridade.map(a -> EntityModel.of(a,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Autoridade>> createAutoridade(@RequestBody Autoridade autoridade) {
        Autoridade createdAutoridade = autoridadeService.createAutoridade(autoridade);
        EntityModel<Autoridade> entityModel = EntityModel.of(createdAutoridade,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(createdAutoridade.getId())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Autoridade>> updateAutoridade(@PathVariable Long id, @RequestBody Autoridade autoridadeDetails) {
        Optional<Autoridade> autoridadeOptional = autoridadeService.updateAutoridade(id, autoridadeDetails);
        return autoridadeOptional.map(a -> EntityModel.of(a,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutoridadeController.class).getAutoridadeById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutoridade(@PathVariable Long id) {
        if (autoridadeService.deleteAutoridade(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
