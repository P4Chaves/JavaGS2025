package br.com.queimadas.alertas.controller;

import br.com.queimadas.alertas.domain.entity.Alerta;
import br.com.queimadas.alertas.domain.entity.Ocorrencia;
import br.com.queimadas.alertas.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para acompanhamento e aceite de {@link Alerta}s.
 *
 * Rotas principais
 * ──────────────────────────────────────────────────────────────
 * • GET  /alertas                 – lista todos os alertas
 * • GET  /alertas/pendentes       – lista apenas os alertas NOVO
 * • POST /alertas/{id}/aceitar    – agente aceita o alerta e abre ocorrência
 *
 *  Exemplo de aceite via cURL:
 *    curl -X POST "http://localhost:8080/alertas/5/aceitar?agenteId=2"
 */
@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService service;

    /* -------------------------------------------------------------------- */
    /* Listagens                                                            */
    /* -------------------------------------------------------------------- */

    /** Lista todos os alertas, independentemente do status. */
    @GetMapping
    public List<Alerta> listarTodos() {
        return service.listarTodos();
    }

    /** Lista apenas alertas com status NOVO. */
    @GetMapping("/pendentes")
    public List<Alerta> listarPendentes() {
        return service.listarPendentes();
    }

    /* -------------------------------------------------------------------- */
    /* Agente aceita alerta e cria ocorrência                               */
    /* -------------------------------------------------------------------- */

    /**
     * Aceita um alerta, vinculando-o ao agente informado
     * e criando automaticamente uma {@link Ocorrencia}.
     *
     * @param id        ID do alerta
     * @param agenteId  ID do agente ambiental que aceita
     * @return Ocorrência recém-criada
     */
    @PostMapping("/{id}/aceitar")
    public ResponseEntity<Ocorrencia> aceitarAlerta(@PathVariable Long id,
                                                    @RequestParam Long agenteId) {
        Ocorrencia ocorr = service.aceitarAlerta(id, agenteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorr);
    }
}
