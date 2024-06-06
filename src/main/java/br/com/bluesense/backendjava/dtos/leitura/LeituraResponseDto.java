package br.com.bluesense.backendjava.dtos.leitura;

import java.time.LocalDateTime;

import br.com.bluesense.backendjava.entities.Leitura;

public record LeituraResponseDto(
  Long id, 
  LocalDateTime dataCriacao,
  String valor
) {

  public LeituraResponseDto(Leitura leitura){
    this(leitura.getId(), leitura.getDataCriacao(), leitura.getValor());
  }
} 
