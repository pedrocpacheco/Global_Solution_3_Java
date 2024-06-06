package br.com.bluesense.backendjava.services;

import br.com.bluesense.backendjava.entities.navio.Navio;
import br.com.bluesense.backendjava.repositories.NavioRepository;
import br.com.bluesense.dtos.navio.NavioRequestDto;
import br.com.bluesense.dtos.navio.NavioResponseDto;
import br.com.bluesense.mappers.NavioMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavioService {

    @Autowired
    private NavioRepository navioRepository;

    public List<NavioResponseDto> getAllNavios() {
        var dtos = NavioMapper.createDtosFromEntities(navioRepository.findAll());
        return dtos;
    }

    public Optional<Navio> getNavioById(Long id) {
        return navioRepository.findById(id);
    }

    public Navio createNavio(NavioRequestDto dto) {
        var navio = NavioMapper.createEntityFromDto(dto);
        return navioRepository.save(navio);
    }

    public Optional<Navio> updateNavio(Long id, Navio navioDetails) {
        return navioRepository.findById(id).map(navio -> {
            navio.setNome(navioDetails.getNome());
            navio.setMarca(navioDetails.getMarca());
            navio.setModelo(navioDetails.getModelo());
            navio.setTipoNavio(navioDetails.getTipoNavio());
            return navioRepository.save(navio);
        });
    }

    public boolean deleteNavio(Long id) {
        return navioRepository.findById(id).map(navio -> {
            navioRepository.delete(navio);
            return true;
        }).orElse(false);
    }
    
}
