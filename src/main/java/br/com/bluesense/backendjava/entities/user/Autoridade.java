package br.com.bluesense.backendjava.entities.user;

import java.util.List;

import br.com.bluesense.backendjava.entities.Notificacao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Autoridade extends Usuario{
    
    private String departamento;

    private String descricao;

    @OneToMany(mappedBy = "autoridade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoes;

}
