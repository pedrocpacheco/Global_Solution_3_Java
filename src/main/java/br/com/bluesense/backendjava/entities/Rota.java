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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ROTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rota")
    private Long id;

    @Column(name = "nm_rota")
    private String nome;

    @Column(name = "origem_rota")
    private String origem;

    @Column(name = "destino_rota")
    private String destino;

    @OneToMany(mappedBy = "rota", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Leitura> leituras;
}
