package br.com.bluesense.backendjava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.entities.Rota;
import br.com.bluesense.backendjava.repositories.RotaRepository;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;

    public Page<Rota> getAllRotas(int page, int size, String sortBy, String sortOrder) {
        // Convertendo as informações de paginação e ordenação para Pageable
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(sortBy).descending());
        }
        return rotaRepository.findAll(pageable);
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
