package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoDTO;
import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoResponseDTO;
import br.com.bluesense.backendjava.services.NotificacaoService;
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
@RequestMapping("/notificacoes")
@Api(tags = "Notificações")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    @ApiOperation(value = "Obter todas as notificações", response = PagedModel.class)
    public ResponseEntity<PagedModel<EntityModel<NotificacaoDTO>>> getAllNotificacoes(
            @ApiParam(value = "Número da página", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Tamanho da página", defaultValue = "3") @RequestParam(defaultValue = "3") int size,
            @ApiParam(value = "Campo para ordenação", defaultValue = "dataCriacao") @RequestParam(defaultValue = "dataCriacao") String sortBy,
            @ApiParam(value = "Ordem de classificação (asc ou desc)", defaultValue = "asc") @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<NotificacaoDTO> notificacoes = notificacaoService.getAllNotificacoes(page, size, sortBy, sortOrder);
        PageMetadata pageMetadata = new PageMetadata(size, page, notificacoes.getTotalElements());
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
    @ApiOperation(value = "Obter uma notificação pelo ID", response = EntityModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Notificação encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Notificação não encontrada")
    })
    public ResponseEntity<EntityModel<NotificacaoDTO>> getNotificacaoById(@ApiParam(value = "ID da notificação") @PathVariable Long id) {
        Optional<NotificacaoDTO> notificacao = notificacaoService.getNotificacaoById(id);
        return notificacao.map(n -> EntityModel.of(n,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar uma nova notificação", response = EntityModel.class)
    public ResponseEntity<EntityModel<NotificacaoResponseDTO>> createNotificacao(@ApiParam(value = "Detalhes da nova notificação a ser criada") @RequestBody NotificacaoDTO notificacaoDTO) {
        NotificacaoResponseDTO createdNotificacao = notificacaoService.createNotificacao(notificacaoDTO);
        EntityModel<NotificacaoResponseDTO> entityModel = EntityModel.of(createdNotificacao,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(createdNotificacao.id())).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma notificação existente", response = EntityModel.class)
    public ResponseEntity<EntityModel<NotificacaoDTO>> updateNotificacao(@ApiParam(value = "ID da notificação a ser atualizada") @PathVariable Long id, 
                                                                          @ApiParam(value = "Detalhes da notificação atualizada") @RequestBody NotificacaoDTO notificacaoDetails) {
        Optional<NotificacaoDTO> notificacaoOptional = notificacaoService.updateNotificacao(id, notificacaoDetails);
        return notificacaoOptional.map(n -> EntityModel.of(n,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).getNotificacaoById(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir uma notificação pelo ID")
    public ResponseEntity<Void> deleteNotificacao(@ApiParam(value = "ID da notificação a ser excluída") @PathVariable Long id) {
        if (notificacaoService.deleteNotificacao(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
