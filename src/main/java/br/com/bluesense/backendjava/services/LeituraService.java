package br.com.bluesense.backendjava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.dtos.leitura.LeituraResponseDto;
import br.com.bluesense.backendjava.entities.Leitura;
import br.com.bluesense.backendjava.infra.exceptions.IncorrectRequestException;
import br.com.bluesense.backendjava.repositories.LeituraRepository;

@Service
public class LeituraService {

    @Autowired
    private LeituraRepository leituraRepository;

    public Page<Leitura> getAllLeituras(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(sortBy).descending());
        }
        return leituraRepository.findAll(pageable);
    }

    public Optional<Leitura> getLeituraById(Long id) {
        return leituraRepository.findById(id);
    }

    public LeituraResponseDto createLeitura(Leitura leitura) {
        try {
            leituraRepository.save(leitura);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("notificacao_id")) {
                throw new IncorrectRequestException("O ID da notificação já existe na tabela de leituras.");
            } else {
                throw e;
            }
        }
        return new LeituraResponseDto(leitura);
    }

    public Optional<Leitura> updateLeitura(Long id, Leitura leituraDetails) {
        return leituraRepository.findById(id).map(leitura -> {
            leitura.setValor(leituraDetails.getValor());
            leitura.setRota(leituraDetails.getRota());
            leitura.setSensor(leituraDetails.getSensor());
            leitura.setNotificacao(leituraDetails.getNotificacao());
            return leituraRepository.save(leitura);
        });
    }

    public boolean deleteLeitura(Long id) {
        return leituraRepository.findById(id).map(leitura -> {
            leituraRepository.delete(leitura);
            return true;
        }).orElse(false);
    }
}
