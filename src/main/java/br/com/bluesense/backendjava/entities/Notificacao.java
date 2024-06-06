package br.com.bluesense.backendjava.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_NOTIFICACAO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensagem_notificacao")
    private String mensagem;

    @CreationTimestamp
    @Column(name = "data_criacao_notificacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "autoridade_id", nullable = false)
    private Autoridade autoridade;

    @OneToOne(mappedBy = "notificacao")
    @JsonIgnore
    private Leitura leitura;

}
