package br.com.bluesense.backendjava.services;

import br.com.bluesense.backendjava.entities.Rota;
import br.com.bluesense.backendjava.repositories.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;

    public List<Rota> getAllRotas() {
        return rotaRepository.findAll();
    }

    public Optional<Rota> getRotaById(Long id) {
        return rotaRepository.findById(id);
    }

    public Rota createRota(Rota rota) {
        return rotaRepository.save(rota);
    }

    public Optional<Rota> updateRota(Long id, Rota rotaDetails) {
        return rotaRepository.findById(id).map(rota -> {
            rota.setNome(rotaDetails.getNome());
            rota.setOrigem(rotaDetails.getOrigem());
            rota.setDestino(rotaDetails.getDestino());
            return rotaRepository.save(rota);
        });
    }

    public boolean deleteRota(Long id) {
        return rotaRepository.findById(id).map(rota -> {
            rotaRepository.delete(rota);
            return true;
        }).orElse(false);
    }
}
