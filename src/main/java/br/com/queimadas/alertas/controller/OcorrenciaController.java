package br.com.queimadas.alertas.controller;

import br.com.queimadas.alertas.domain.entity.Ocorrencia;
import br.com.queimadas.alertas.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para acompanhamento e encerramento de {@link Ocorrencia}s.
 *
 * Rotas principais
 * ──────────────────────────────────────────────────────────────
 * • GET  /ocorrencias                    – lista todas
 * • GET  /ocorrencias/{id}               – detalhes por ID
 * • PUT  /ocorrencias/{id}/encerrar      – encerra ocorrência
 *
 * Exemplo de encerramento via cURL:
 *   curl -X PUT "http://localhost:8080/ocorrencias/3/encerrar" \
 *        -H "Content-Type: text/plain" \
 *        -d "Foi realizada contenção do foco com abafadores e linha d'água."
 */
@RestController
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    /* -------------------------------------------------------------------- */
    /* Listagens                                                            */
    /* -------------------------------------------------------------------- */

    /** Lista todas as ocorrências. */
    @GetMapping
    public List<Ocorrencia> listarTodas() {
        return service.listarTodas();
    }

    /** Retorna uma ocorrência específica pelo ID. */
    @GetMapping("/{id}")
    public Ocorrencia detalhes(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /* -------------------------------------------------------------------- */
    /* Encerramento                                                         */
    /* -------------------------------------------------------------------- */

    /**
     * Encerra a ocorrência informada, registrando as ações tomadas.
     *
     * @param id            ID da ocorrência
     * @param acoesTomadas  descrição livre das ações executadas
     * @return Ocorrência atualizada
     */
    @PutMapping("/{id}/encerrar")
    public ResponseEntity<Ocorrencia> encerrar(@PathVariable Long id,
                                               @RequestBody String acoesTomadas) {
        Ocorrencia encerrada = service.encerrar(id, acoesTomadas);
        return ResponseEntity.status(HttpStatus.OK).body(encerrada);
    }
}
