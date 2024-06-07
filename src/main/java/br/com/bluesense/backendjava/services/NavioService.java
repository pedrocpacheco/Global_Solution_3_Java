package br.com.bluesense.backendjava.services;

import br.com.bluesense.backendjava.dtos.navio.NavioRequestDto;
import br.com.bluesense.backendjava.dtos.navio.NavioResponseDto;
import br.com.bluesense.backendjava.entities.navio.Navio;
import br.com.bluesense.backendjava.mappers.NavioMapper;
import br.com.bluesense.backendjava.repositories.NavioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NavioService {

    @Autowired
    private NavioRepository navioRepository;

    public Page<NavioResponseDto> getAllNavios(Pageable pageable) {
        Page<Navio> navios = navioRepository.findAll(pageable);
        return navios.map(NavioMapper::entityToResponseDto);
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
