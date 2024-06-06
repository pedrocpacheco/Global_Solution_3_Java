package br.com.bluesense.backendjava.dtos.navio;

import br.com.bluesense.backendjava.entities.navio.TipoNavio;

public record NavioResponseDto(
  String nome,
  String marca,
  String modelo,
  TipoNavio tiponavio
) {
  
}
