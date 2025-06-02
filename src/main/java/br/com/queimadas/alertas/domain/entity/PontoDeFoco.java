package br.com.queimadas.alertas.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Registro de um ponto de calor ou fumaça detectado por um {@link Sensor}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_PONTO_FOCO")
public class PontoDeFoco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Sensor que originou o ponto de foco */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sensor sensor;

    /** Intensidade (0–1000) do foco de calor/fumaça */
    @Column(nullable = false)
    private double intensidade;

    /** Data/hora da detecção (UTC) */
    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    /* -------------------------------------------------------------------- */
    /* Regras de domínio                                                    */
    /* -------------------------------------------------------------------- */

    /**
     * Determina se este ponto de foco requer abertura de alerta.
     * @return true se intensidade >= 300
     */
    public boolean precisaAlerta() {
        return intensidade >= 300.0;
    }
}
