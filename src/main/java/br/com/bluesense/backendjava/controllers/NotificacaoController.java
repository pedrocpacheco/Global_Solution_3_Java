package br.com.bluesense.backendjava.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoDTO;
import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoResponseDTO;
import br.com.bluesense.backendjava.services.NotificacaoService;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    public List<NotificacaoDTO> getAllNotificacoes() {
        return notificacaoService.getAllNotificacoes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoDTO> getNotificacaoById(@PathVariable Long id) {
        Optional<NotificacaoDTO> notificacao = notificacaoService.getNotificacaoById(id);
        return notificacao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public NotificacaoResponseDTO createNotificacao(@RequestBody NotificacaoDTO notificacao) {
        return notificacaoService.createNotificacao(notificacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoDTO> updateNotificacao(@PathVariable Long id, @RequestBody NotificacaoDTO notificacaoDetails) {
        Optional<NotificacaoDTO> notificacaoOptional = notificacaoService.updateNotificacao(id, notificacaoDetails);
        return notificacaoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
