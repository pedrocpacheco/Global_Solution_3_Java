package br.com.bluesense.backendjava.entities.navio;

import br.com.bluesense.backendjava.entities.Rota;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NAVIO_ROTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NavioRota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "navio_id")
    private Navio navio;

    @ManyToOne
    @JoinColumn(name = "rota_id")
    private Rota rota;

}