package br.com.bluesense.backendjava.entities.sensor;

import java.util.List;

import br.com.bluesense.backendjava.entities.Leitura;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_SENSOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id;

    @Column(name = "modelo_sensor")
    private String modelo;

    @Column(name = "marca_sensor")
    private String marca;

    @Enumerated(EnumType.STRING)
    private TipoSensor tipoSensor;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leitura> leituras;

}
