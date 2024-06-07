package br.com.bluesense.backendjava.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.dtos.navio.NavioRotaDto;
import br.com.bluesense.backendjava.entities.navio.NavioRota;
import br.com.bluesense.backendjava.infra.exceptions.IncorrectRequestException;
import br.com.bluesense.backendjava.repositories.NavioRepository;
import br.com.bluesense.backendjava.repositories.NavioRotaRepository;
import br.com.bluesense.backendjava.repositories.RotaRepository;

@Service
public class NavioRotaService {

    @Autowired 
    NavioRotaRepository navioRotaRepository;

    @Autowired
    private NavioRepository navioRepository;

    @Autowired
    private RotaRepository rotaRepository;

    public List<NavioRota> getAllNavioRotas() {
        return navioRotaRepository.findAll();
    }

    public NavioRota getNavioRotaById(Long id) {
        return navioRotaRepository.findById(id).orElse(null);
    }

    public NavioRota saveNavioRota(NavioRotaDto navioRotaDto) {
      try{
        var navio = navioRepository.findById(navioRotaDto.navioId()).get();
        var rota = rotaRepository.findById(navioRotaDto.rotaId()).get();
        var navioRota = new NavioRota();
        navioRota.setNavio(navio);
        navioRota.setRota(rota);
        return navioRotaRepository.save(navioRota);
      } catch(RuntimeException e){
        throw new IncorrectRequestException("Os IDs informados não existem");
      }

    }

    public void deleteNavioRota(Long id) {
        navioRotaRepository.deleteById(id);
    }
}