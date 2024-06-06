package br.com.bluesense.dtos.navio;

import br.com.bluesense.backendjava.entities.navio.TipoNavio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NavioRequestDto(
  @NotBlank(message = "O nome não pode ser nulo")
  String nome,
  @NotBlank(message = "A marca não pode ser nula")
  String marca,
  @NotBlank(message = "O modelo não pode ser nulo")
  String modelo,
  @NotNull(message = "O tiponavio nao pode ser nulo")
  TipoNavio tipoNavio
) {
}
