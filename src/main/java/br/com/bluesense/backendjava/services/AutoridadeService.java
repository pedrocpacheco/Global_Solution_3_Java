package br.com.bluesense.backendjava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.entities.Autoridade;
import br.com.bluesense.backendjava.repositories.AutoridadeRepository;

@Service
public class AutoridadeService {

    @Autowired
    private AutoridadeRepository autoridadeRepository;

    public List<Autoridade> getAllAutoridades() {
        return autoridadeRepository.findAll();
    }

    public Optional<Autoridade> getAutoridadeById(Long id) {
        return autoridadeRepository.findById(id);
    }

    public Autoridade createAutoridade(Autoridade autoridade) {
        return autoridadeRepository.save(autoridade);
    }

    public Optional<Autoridade> updateAutoridade(Long id, Autoridade autoridadeDetails) {
        return autoridadeRepository.findById(id).map(autoridade -> {
            autoridade.setNome(autoridadeDetails.getNome());
            autoridade.setEmail(autoridadeDetails.getEmail());
            autoridade.setSenha(autoridadeDetails.getSenha());
            autoridade.setDepartamento(autoridadeDetails.getDepartamento());
            autoridade.setDescricao(autoridadeDetails.getDescricao());
            return autoridadeRepository.save(autoridade);
        });
    }

    public boolean deleteAutoridade(Long id) {
        return autoridadeRepository.findById(id).map(autoridade -> {
            autoridadeRepository.delete(autoridade);
            return true;
        }).orElse(false);
    }
}

