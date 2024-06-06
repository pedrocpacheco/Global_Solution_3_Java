package br.com.bluesense.backendjava.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_AUTORIDADE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Autoridade{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_usuario")
    private String nome;

    @Column(name = "email_usuario")
    private String email;

    @Column(name = "senha_usuario")
    private String senha;

    @Column(name = "departamento_usuario")
    private String departamento;

    @Column(name = "descricao_usuario")
    private String descricao;

    @OneToMany(mappedBy = "autoridade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoes;

}
