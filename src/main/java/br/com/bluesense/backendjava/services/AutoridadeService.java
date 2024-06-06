package br.com.bluesense.backendjava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.entities.Autoridade;
import br.com.bluesense.backendjava.repositories.AutoridadeRepository;

@Service
public class AutoridadeService {

    @Autowired
    private AutoridadeRepository autoridadeRepository;

    public Page<Autoridade> getAllAutoridades(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(sortBy).descending());
        }
        return autoridadeRepository.findAll(pageable);
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
