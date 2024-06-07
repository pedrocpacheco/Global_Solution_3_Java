package br.com.bluesense.backendjava.entities.sensor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "O modelo do sensor não pode estar em branco")
    private String modelo;

    @Column(name = "marca_sensor")
    @NotBlank(message = "A marca do sensor não pode estar em branco")
    private String marca;

    @Enumerated(EnumType.STRING)
    private TipoSensor tipoSensor;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Leitura> leituras;

    
}
