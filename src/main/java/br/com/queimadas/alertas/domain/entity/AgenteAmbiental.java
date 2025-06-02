package br.com.queimadas.alertas.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um agente ou equipe responsável por atuar em campo
 * quando um {@link Alerta} é gerado.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_AGENTE_AMBIENTAL")
public class AgenteAmbiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome do agente ou da equipe */
    @Column(nullable = false, length = 80)
    private String nome;

    /** Base operacional (cidade ou posto avançado) */
    @Column(nullable = false, length = 120)
    private String baseOperacional;

    /** Contato telefônico (opcional) */
    @Column(length = 20)
    private String telefone;

    /** E-mail institucional (opcional) */
    @Column(length = 80)
    private String email;

    /* -------------------------------------------------------------- */
    /* Regras de domínio utilitárias                                   */
    /* -------------------------------------------------------------- */

    public void atualizarContato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }
}
