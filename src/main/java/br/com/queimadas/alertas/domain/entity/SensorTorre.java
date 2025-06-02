package br.com.queimadas.alertas.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sensor fixo instalado no topo de torres de monitoramento.
 * Complementa a hierarquia de {@link Sensor}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("TORRE")   // valor gravado na coluna TIPO_SENSOR
public class SensorTorre extends Sensor {

    /** Altura da torre em metros */
    private double altura;

    /** Alcance de detecção em quilômetros (raio) */
    private double alcanceKm;
}
