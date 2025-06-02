package br.com.queimadas.alertas.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Alerta gerado a partir de um {@link PontoDeFoco} cuja intensidade excede um limiar.
 * Criado exclusivamente pelo {@link br.com.queimadas.alertas.domain.factory.AlertaFactory}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_ALERTA")
public class Alerta {

    /* -------------------------------------------------------------------- */
    /* Enumerações                                                          */
    /* -------------------------------------------------------------------- */

    public enum Severidade { BAIXA, MEDIA, ALTA }

    public enum Status { NOVO, ENVIADO, ACEITO }

    /* -------------------------------------------------------------------- */
    /* Atributos                                                            */
    /* -------------------------------------------------------------------- */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Associação 1-1 com o ponto de foco que originou o alerta */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FOCO_ID")
    private PontoDeFoco pontoDeFoco;

    /** Severidade calculada pelo {@link br.com.queimadas.alertas.domain.factory.AlertaFactory} */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Severidade severidade;

    /** Estado do alerta no fluxo de trabalho */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status = Status.NOVO;

    /** Momento da criação (UTC) */
    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}
