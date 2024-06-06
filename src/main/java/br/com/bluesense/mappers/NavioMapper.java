package br.com.bluesense.mappers;

import java.util.List;
import java.util.stream.Collectors;

import br.com.bluesense.backendjava.entities.navio.Navio;
import br.com.bluesense.dtos.navio.NavioRequestDto;
import br.com.bluesense.dtos.navio.NavioResponseDto;

public abstract class NavioMapper {

  public static Navio createEntityFromDto(NavioRequestDto dto) {
    var entity = new Navio(
        null, 
        dto.nome(),
        dto.marca(),
        dto.modelo(),
        dto.tipoNavio()
    );
    return entity;
  }

  public static List<NavioResponseDto> createDtosFromEntities(List<Navio> entities) {
    return entities.stream()
        .map(navio -> new NavioResponseDto(
            navio.getNome(),
            navio.getMarca(),
            navio.getModelo(),
            navio.getTipoNavio()
        ))
        .collect(Collectors.toList());
  }

}
