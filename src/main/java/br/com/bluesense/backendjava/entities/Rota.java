package br.com.bluesense.backendjava.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "TB_ROTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rota {
    
    @Id
    private Long id;

    @Column(name = "nm_origem")
    private String nome;

    @Column(name = "origem_rota")
    private String origem;

    @Column(name = "origem_destino")
    private String destino;

    @OneToMany(mappedBy = "rota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leitura> leituras;
}
