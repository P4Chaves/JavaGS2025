package br.com.queimadas.alertas.controller;

import br.com.queimadas.alertas.domain.entity.PontoDeFoco;
import br.com.queimadas.alertas.service.PontoDeFocoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Endpoints REST para registro e consulta de {@link PontoDeFoco}.
 *
 * Rotas principais:
 * • GET  /pontos-de-foco                    – lista todos
 * • GET  /pontos-de-foco/intervalo          – lista entre datas
 * • POST /pontos-de-foco                    – registra novo ponto (gera alerta se necessário)
 */
@RestController
@RequestMapping("/pontos-de-foco")
@RequiredArgsConstructor
public class PontoDeFocoController {

    private final PontoDeFocoService service;

    /* -------------------------------------------------------------------- */
    /* Listagem geral                                                       */
    /* -------------------------------------------------------------------- */

    /** Lista todos os pontos de foco registrados. */
    @GetMapping
    public List<PontoDeFoco> listarTodos() {
        return service.listarTodos();
    }

    /* -------------------------------------------------------------------- */
    /* Listagem por intervalo                                               */
    /* -------------------------------------------------------------------- */

    /**
     * Lista pontos de foco ocorridos dentro de um intervalo.
     * Exemplo de chamada:
     *   GET /pontos-de-foco/intervalo?inicio=2025-05-01T00:00:00&fim=2025-05-31T23:59:59
     */
    @GetMapping("/intervalo")
    public List<PontoDeFoco> listarEntre(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        return service.listarEntre(inicio, fim);
    }

    /* -------------------------------------------------------------------- */
    /* Registro de novo ponto de foco                                       */
    /* -------------------------------------------------------------------- */

    /**
     * Registra um novo ponto de foco.
     * Body JSON mínimo:
     * {
     *   "sensor": { "id": 1 },
     *   "intensidade": 450.0
     * }
     */
    @PostMapping
    public ResponseEntity<PontoDeFoco> registrar(@RequestBody PontoDeFoco foco) {
        PontoDeFoco salvo = service.registrar(foco);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
