package br.com.bluesense.backendjava.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import br.com.bluesense.backendjava.entities.sensor.Sensor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_LEITURA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leitura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "valor_leitura")
    @NotBlank(message = "O valor da leitura não pode estar em branco")
    private String valor;

    @ManyToOne
    @JoinColumn(name = "rota_id", nullable = false)
    @NotNull(message = "A leitura deve estar associada a uma rota")
    private Rota rota;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    @NotNull(message = "A leitura deve estar associada a um sensor")
    private Sensor sensor;

    @OneToOne
    private Notificacao notificacao;

}
