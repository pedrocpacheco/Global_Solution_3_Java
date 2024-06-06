package br.com.bluesense.backendjava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.dtos.leitura.LeituraResponseDto;
import br.com.bluesense.backendjava.entities.Leitura;
import br.com.bluesense.backendjava.infra.exceptions.IncorrectRequestException;
import br.com.bluesense.backendjava.repositories.LeituraRepository;

@Service
public class LeituraService {

    @Autowired
    private LeituraRepository leituraRepository;

    public List<Leitura> getAllLeituras() {
        return leituraRepository.findAll();
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
        var responseDto = new LeituraResponseDto(leitura); 
        return responseDto;
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
