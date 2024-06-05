package br.com.bluesense.backendjava.entities.navio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_NAVIO")
public class Navio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_navio")
    private String nome;

    @Column(name = "marca_navio")
    private String marca;

    @Column(name = "modelo_navio")
    private String modelo;

    @Enumerated(EnumType.STRING)
    private TipoNavio tipoNavio;

}
