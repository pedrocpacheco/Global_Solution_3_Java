package br.com.bluesense.backendjava.dtos.navio;

import jakarta.validation.constraints.NotNull;

public record NavioRotaDto(
  @NotNull(message = "ID do Navio não pode ser nulo")
  Long navioId,
  @NotNull(message = "ID da Rota não pode ser nulo")
  Long rotaId
) {
  
}
