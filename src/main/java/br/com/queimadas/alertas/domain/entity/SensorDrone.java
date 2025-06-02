package br.com.queimadas.alertas.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sensor embarcado em drone.
 * Demonstra herança/polimorfismo a partir da classe abstrata {@link Sensor}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("DRONE")   // valor gravado na coluna TIPO_SENSOR
public class SensorDrone extends Sensor {

    /** Altitude máxima operacional (metros) */
    private double altitudeMaxima;

    /** Autonomia de voo (minutos) */
    private int autonomiaMinutos;
}
