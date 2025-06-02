package br.com.queimadas.alertas.service;

import br.com.queimadas.alertas.domain.entity.*;
import br.com.queimadas.alertas.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciamento do ciclo de vida dos {@link Alerta}s.
 */
@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepo;
    private final AgenteAmbientalRepository agenteRepo;
    private final OcorrenciaRepository ocorrRepo;

    /* -------------------------------------------------------------------- */
    /* Consultas básicas                                                    */
    /* -------------------------------------------------------------------- */

    /** Retorna todos os alertas com status NOVO. */
    public List<Alerta> listarPendentes() {
        return alertaRepo.findByStatus(Alerta.Status.NOVO);
    }

    /** Retorna todos os alertas, independentemente do status. */
    public List<Alerta> listarTodos() {
        return alertaRepo.findAll();
    }

    /* -------------------------------------------------------------------- */
    /* Fluxo de trabalho                                                    */
    /* -------------------------------------------------------------------- */

    /**
     * Aceita um alerta, vinculando-o a um agente ambiental
     * e abrindo automaticamente uma {@link Ocorrencia}.
     *
     * @param alertaId id do alerta
     * @param agenteId id do agente
     * @return Ocorrência recém-criada
     */
    @Transactional
    public Ocorrencia aceitarAlerta(Long alertaId, Long agenteId) {
        Alerta alerta = alertaRepo.findById(alertaId)
                .orElseThrow(() -> new IllegalArgumentException("Alerta não encontrado: " + alertaId));

        if (alerta.getStatus() != Alerta.Status.NOVO) {
            throw new IllegalStateException("Alerta já processado.");
        }

        AgenteAmbiental agente = agenteRepo.findById(agenteId)
                .orElseThrow(() -> new IllegalArgumentException("Agente não encontrado: " + agenteId));

        // Atualiza status do alerta
        alerta.setStatus(Alerta.Status.ACEITO);
        alertaRepo.save(alerta);

        // Cria ocorrência vinculada
        Ocorrencia ocorr = new Ocorrencia();
        ocorr.setAlerta(alerta);
        ocorr.setAgente(agente);
        return ocorrRepo.save(ocorr);
    }
}
