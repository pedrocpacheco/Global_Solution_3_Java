package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoDTO;
import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoResponseDTO;
import br.com.bluesense.backendjava.services.NotificacaoService;
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
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<NotificacaoDTO>>> getAllNotificacoes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "dataCriacao") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<NotificacaoDTO> notificacoes = notificacaoService.getAllNotificacoes(page, size, sortBy, sortOrder);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, notificacoes.getTotalElements());
        PagedModel<EntityModel<NotificacaoDTO>> pagedModel = PagedModel.of(
                notificacoes.getContent().stream()
                        .map(notificacao -> EntityModel.of(notificacao,
                                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(notificacao.getId())).withSelfRel()))
                        .collect(Collectors.toList()),
                pageMetadata
        );

        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class)
                .getAllNotificacoes(page, size, sortBy, sortOrder)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NotificacaoDTO>> getNotificacaoById(@PathVariable Long id) {
        Optional<NotificacaoDTO> notificacao = notificacaoService.getNotificacaoById(id);
        return notificacao.map(n -> EntityModel.of(n,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<NotificacaoResponseDTO>> createNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
        NotificacaoResponseDTO createdNotificacao = notificacaoService.createNotificacao(notificacaoDTO);
        EntityModel<NotificacaoResponseDTO> entityModel = EntityModel.of(createdNotificacao,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(createdNotificacao.id())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<NotificacaoDTO>> updateNotificacao(@PathVariable Long id, @RequestBody NotificacaoDTO notificacaoDetails) {
        Optional<NotificacaoDTO> notificacaoOptional = notificacaoService.updateNotificacao(id, notificacaoDetails);
        return notificacaoOptional.map(n -> EntityModel.of(n,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacao(@PathVariable Long id) {
        if (notificacaoService.deleteNotificacao(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
