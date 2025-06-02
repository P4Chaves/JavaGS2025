package br.com.queimadas.alertas.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Registro da intervenção em campo gerada a partir de um {@link Alerta}.
 * Acompanha o ciclo de vida da operação até o seu encerramento.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_OCORRENCIA")
public class Ocorrencia {

    /* -------------------------------------------------------------------- */
    /* Enum interno para status                                             */
    /* -------------------------------------------------------------------- */
    public enum Status { ABERTA, FECHADA }

    /* -------------------------------------------------------------------- */
    /* Atributos                                                             */
    /* -------------------------------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Alerta que originou esta ocorrência (1 : 1) */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ALERTA_ID")
    private Alerta alerta;

    /** Agente ou equipe responsável pela ação */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AgenteAmbiental agente;

    /** Status da ocorrência (ABERTA / FECHADA) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status = Status.ABERTA;

    /** Quando a ação iniciou (UTC) */
    @Column(nullable = false)
    private LocalDateTime horaInicio = LocalDateTime.now();

    /** Quando a ação encerrou (UTC) */
    private LocalDateTime horaFim;

    /** Descrição das ações tomadas em campo (texto livre) */
    @Column(columnDefinition = "TEXT")
    private String acoesTomadas;

    /* -------------------------------------------------------------------- */
    /* Regras de domínio                                                    */
    /* -------------------------------------------------------------------- */

    /** Encerrar a ocorrência registrando hora fim e ações executadas */
    public void encerrar(String acoes) {
        if (this.status == Status.FECHADA) {
            throw new IllegalStateException("Ocorrência já encerrada.");
        }
        this.acoesTomadas = acoes;
        this.horaFim = LocalDateTime.now();
        this.status = Status.FECHADA;
    }

    /** Retorna a duração em minutos, ou -1 se não finalizada */
    public long duracaoMinutos() {
        if (horaFim == null) return -1;
        return Duration.between(horaInicio, horaFim).toMinutes();
    }
}
