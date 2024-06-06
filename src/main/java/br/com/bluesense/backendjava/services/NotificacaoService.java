package br.com.bluesense.backendjava.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesense.backendjava.entities.Autoridade;
import br.com.bluesense.backendjava.entities.Notificacao;
import br.com.bluesense.backendjava.repositories.NotificacaoRepository;
import br.com.bluesense.dtos.autoridade.AutoridadeDTO;
import br.com.bluesense.dtos.notificacao.NotificacaoDTO;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public List<NotificacaoDTO> getAllNotificacoes() {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        return notificacoes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<NotificacaoDTO> getNotificacaoById(Long id) {
        return notificacaoRepository.findById(id).map(this::convertToDTO);
    }

    public NotificacaoDTO createNotificacao(NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = convertToEntity(notificacaoDTO);
        Notificacao savedNotificacao = notificacaoRepository.save(notificacao);
        return convertToDTO(savedNotificacao);
    }

    public Optional<NotificacaoDTO> updateNotificacao(Long id, NotificacaoDTO notificacaoDetails) {
        return notificacaoRepository.findById(id).map(notificacao -> {
            notificacao.setMensagem(notificacaoDetails.getMensagem());
            notificacao.setAutoridade(convertAutoridadeDTOToEntity(notificacaoDetails.getAutoridade()));
            Notificacao updatedNotificacao = notificacaoRepository.save(notificacao);
            return convertToDTO(updatedNotificacao);
        });
    }

    public boolean deleteNotificacao(Long id) {
        return notificacaoRepository.findById(id).map(notificacao -> {
            notificacaoRepository.delete(notificacao);
            return true;
        }).orElse(false);
    }

    private NotificacaoDTO convertToDTO(Notificacao notificacao) {
        AutoridadeDTO autoridadeDTO = new AutoridadeDTO();
        Autoridade autoridade = notificacao.getAutoridade();
        autoridadeDTO.setId(autoridade.getId());
        autoridadeDTO.setNome(autoridade.getNome());
        autoridadeDTO.setEmail(autoridade.getEmail());
        autoridadeDTO.setDepartamento(autoridade.getDepartamento());
        autoridadeDTO.setDescricao(autoridade.getDescricao());

        NotificacaoDTO notificacaoDTO = new NotificacaoDTO();
        notificacaoDTO.setId(notificacao.getId());
        notificacaoDTO.setMensagem(notificacao.getMensagem());
        notificacaoDTO.setDataCriacao(notificacao.getDataCriacao());
        notificacaoDTO.setAutoridade(autoridadeDTO);

        return notificacaoDTO;
    }

    private Notificacao convertToEntity(NotificacaoDTO notificacaoDTO) {
        Autoridade autoridade = convertAutoridadeDTOToEntity(notificacaoDTO.getAutoridade());

        Notificacao notificacao = new Notificacao();
        notificacao.setId(notificacaoDTO.getId());
        notificacao.setMensagem(notificacaoDTO.getMensagem());
        notificacao.setDataCriacao(notificacaoDTO.getDataCriacao());
        notificacao.setAutoridade(autoridade);

        return notificacao;
    }

    private Autoridade convertAutoridadeDTOToEntity(AutoridadeDTO autoridadeDTO) {
        Autoridade autoridade = new Autoridade();
        autoridade.setId(autoridadeDTO.getId());
        autoridade.setNome(autoridadeDTO.getNome());
        autoridade.setEmail(autoridadeDTO.getEmail());
        autoridade.setDepartamento(autoridadeDTO.getDepartamento());
        autoridade.setDescricao(autoridadeDTO.getDescricao());

        return autoridade;
    }
}
