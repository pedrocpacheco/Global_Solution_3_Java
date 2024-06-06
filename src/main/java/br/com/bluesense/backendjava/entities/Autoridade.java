package br.com.bluesense.backendjava.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_AUTORIDADE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Autoridade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_autoridade")
    @NotBlank(message = "O nome da Autoridade não pode estar em branco")
    private String nome;

    @Column(name = "email_autoridade")
    @Email(message = "Por favor, insira um endereço de e-mail válido para a Autoridade")
    private String email;

    @Column(name = "senha_autoridade")
    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @Column(name = "departamento_autoridade")
    @NotBlank(message = "O departamento não pode estar em branco")
    private String departamento;

    @Column(name = "descricao_autoridade")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @OneToMany(mappedBy = "autoridade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Notificacao> notificacoes;

}
