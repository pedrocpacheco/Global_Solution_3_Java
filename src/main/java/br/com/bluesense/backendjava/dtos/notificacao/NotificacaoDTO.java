package br.com.bluesense.backendjava.dtos.notificacao;

import java.time.LocalDateTime;

import br.com.bluesense.backendjava.dtos.autoridade.AutoridadeDTO;

public class NotificacaoDTO {
  private Long id;
  private String mensagem;
  private LocalDateTime dataCriacao;
  private AutoridadeDTO autoridade;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getMensagem() {
    return mensagem;
  }
  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }
  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }
  public void setDataCriacao(LocalDateTime dataCriacao) {
    this.dataCriacao = dataCriacao;
  }
  public AutoridadeDTO getAutoridade() {
    return autoridade;
  }
  public void setAutoridade(AutoridadeDTO autoridade) {
    this.autoridade = autoridade;
  }

  
}
