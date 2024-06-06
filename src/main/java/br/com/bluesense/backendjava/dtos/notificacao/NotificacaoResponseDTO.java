package br.com.bluesense.backendjava.dtos.notificacao;

import java.time.LocalDateTime;

import br.com.bluesense.backendjava.entities.Notificacao;

public record NotificacaoResponseDTO(
  Long id,
  String mensagem,
  LocalDateTime dataCriacao
) {
  public NotificacaoResponseDTO(Notificacao notificacao){
    this(notificacao.getId(), notificacao.getMensagem(), notificacao.getDataCriacao());
  }
}
