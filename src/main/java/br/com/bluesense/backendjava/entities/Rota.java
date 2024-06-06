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
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "O nome da rota não pode estar em branco")
    private String nome;

    @Column(name = "origem_rota")
    @NotBlank(message = "A origem da rota não pode estar em branco")
    private String origem;

    @Column(name = "destino_rota")
    @NotBlank(message = "O destino da rota não pode estar em branco")
    private String destino;

    @OneToMany(mappedBy = "rota", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Leitura> leituras;
}
