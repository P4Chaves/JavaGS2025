package br.com.queimadas.alertas.controller;

import br.com.queimadas.alertas.domain.vo.RelatorioResumo;
import br.com.queimadas.alertas.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Endpoint REST para geração de relatórios estatísticos.
 *
 * Rota principal
 * ──────────────────────────────────────────────────────────────
 * GET /relatorios?inicio=YYYY-MM-DD&fim=YYYY-MM-DD
 *
 * Exemplo de chamada:
 *   GET /relatorios?inicio=2025-05-01&fim=2025-05-31
 */
@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    /**
     * Retorna um resumo estatístico entre as datas informadas (inclusive).
     *
     * @param inicio data inicial  (YYYY-MM-DD)
     * @param fim    data final    (YYYY-MM-DD)
     * @return objeto {@link RelatorioResumo} em JSON
     */
    @GetMapping
    public RelatorioResumo gerar(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return service.gerar(inicio, fim);
    }
}
