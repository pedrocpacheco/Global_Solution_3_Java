package br.com.bluesense.backendjava.services;

import br.com.bluesense.backendjava.dtos.autoridade.AutoridadeDTO;
import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoDTO;
import br.com.bluesense.backendjava.dtos.notificacao.NotificacaoResponseDTO;
import br.com.bluesense.backendjava.entities.Autoridade;
import br.com.bluesense.backendjava.entities.Notificacao;
import br.com.bluesense.backendjava.repositories.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Page<NotificacaoDTO> getAllNotificacoes(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(sortBy).descending());
        }
        return notificacaoRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Optional<NotificacaoDTO> getNotificacaoById(Long id) {
        return notificacaoRepository.findById(id).map(this::convertToDTO);
    }

    public NotificacaoResponseDTO createNotificacao(NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = convertToEntity(notificacaoDTO);
        try {
            notificacaoRepository.save(notificacao);
        } catch (DataIntegrityViolationException e) {
            throw e;
        }
        return new NotificacaoResponseDTO(notificacao);
    }

    public Optional<NotificacaoDTO> updateNotificacao(Long id, NotificacaoDTO notificacaoDetails) {
        return notificacaoRepository.findById(id).map(notificacao -> {
            notificacao.setMensagem(notificacaoDetails.getMensagem());
            notificacao.setAutoridade(convertAutoridadeDTOToEntity(notificacaoDetails.getAutoridade()));
            return notificacaoRepository.save(notificacao);
        }).map(this::convertToDTO);
    }

    public boolean deleteNotificacao(Long id) {
        return notificacaoRepository.findById(id).map(notificacao -> {
            notificacaoRepository.delete(notificacao);
            return true;
        }).orElse(false);
    }

    private NotificacaoDTO convertToDTO(Notificacao notificacao) {
        Autoridade autoridade = notificacao.getAutoridade();
        AutoridadeDTO autoridadeDTO = new AutoridadeDTO();
        if (autoridade != null) {
            autoridadeDTO.setId(autoridade.getId());
            autoridadeDTO.setNome(autoridade.getNome());
            autoridadeDTO.setEmail(autoridade.getEmail());
            autoridadeDTO.setDepartamento(autoridade.getDepartamento());
            autoridadeDTO.setDescricao(autoridade.getDescricao());
        }

        NotificacaoDTO notificacaoDTO = new NotificacaoDTO();
        notificacaoDTO.setId(notificacao.getId());
        notificacaoDTO.setMensagem(notificacao.getMensagem());
        notificacaoDTO.setDataCriacao(notificacao.getDataCriacao());
        notificacaoDTO.setAutoridade(autoridadeDTO);

        return notificacaoDTO;
    }

    private Notificacao convertToEntity(NotificacaoDTO notificacaoDTO) {
        AutoridadeDTO autoridadeDTO = notificacaoDTO.getAutoridade();
        Autoridade autoridade = new Autoridade();
        if (autoridadeDTO != null) {
            autoridade.setId(autoridadeDTO.getId());
            autoridade.setNome(autoridadeDTO.getNome());
            autoridade.setEmail(autoridadeDTO.getEmail());
            autoridade.setDepartamento(autoridadeDTO.getDepartamento());
            autoridade.setDescricao(autoridadeDTO.getDescricao());
        }

        Notificacao notificacao = new Notificacao();
        notificacao.setId(notificacaoDTO.getId());
        notificacao.setMensagem(notificacaoDTO.getMensagem());
        notificacao.setDataCriacao(notificacaoDTO.getDataCriacao());
        notificacao.setAutoridade(autoridade);

        return notificacao;
    }

    private Autoridade convertAutoridadeDTOToEntity(AutoridadeDTO autoridadeDTO) {
        Autoridade autoridade = new Autoridade();
        if (autoridadeDTO != null) {
            autoridade.setId(autoridadeDTO.getId());
            autoridade.setNome(autoridadeDTO.getNome());
            autoridade.setEmail(autoridadeDTO.getEmail());
            autoridade.setDepartamento(autoridadeDTO.getDepartamento());
            autoridade.setDescricao(autoridadeDTO.getDescricao());
        }
        return autoridade;
    }
}
