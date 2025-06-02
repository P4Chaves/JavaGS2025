package br.com.queimadas.alertas.domain.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade abstrata que representa um sensor de monitoramento de queimadas.
 * Pode ser estendida por tipos específicos, como {@link SensorDrone} e {@link SensorTorre}.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SensorDrone.class,  name = "DRONE"),
        @JsonSubTypes.Type(value = SensorTorre.class,  name = "TORRE")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_SENSOR")
public abstract class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Descrição curta ou código do sensor (ex.: “DRONE-01”) */
    @Column(nullable = false, unique = true, length = 40)
    private String codigo;

    /** Latitude em graus decimais (WGS-84) */
    @Column(nullable = false)
    private double latitude;

    /** Longitude em graus decimais (WGS-84) */
    @Column(nullable = false)
    private double longitude;

    /** Define se o sensor está ativo para enviar leituras */
    @Column(nullable = false)
    private boolean ativo = true;

    /** Ativa o sensor */
    public void ativar() {
        this.ativo = true;
    }

    /** Desativa o sensor */
    public void desativar() {
        this.ativo = false;
    }
}
