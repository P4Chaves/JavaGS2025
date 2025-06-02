package br.com.queimadas.alertas.service;

import br.com.queimadas.alertas.domain.vo.RelatorioResumo;
import br.com.queimadas.alertas.repository.*;
import br.com.queimadas.alertas.domain.entity.Ocorrencia;   // <-- ADICIONADO
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;                                   // <-- ADICIONADO

/**
 * Camada de serviço que consolida métricas de desempenho
 * (focos, alertas, tempo médio de resposta) para um intervalo de datas.
 */
@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PontoDeFocoRepository focoRepo;
    private final AlertaRepository alertaRepo;
    private final OcorrenciaRepository ocorrRepo;

    /**
     * Gera um relatório agregando estatísticas entre as datas informadas.
     *
     * @param inicio data inicial (inclusiva)
     * @param fim    data final   (inclusiva)
     * @return resumo estatístico pronto para serializar em JSON
     */
    public RelatorioResumo gerar(LocalDate inicio, LocalDate fim) {

        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime fimDT = fim.plusDays(1).atStartOfDay().minusSeconds(1);

        long totalFocos   = focoRepo.countByDataHoraBetween(inicioDT, fimDT);
        long totalAlertas = alertaRepo.countByCriadoEmBetween(inicioDT, fimDT);

        // busca ocorrências FECHADAS no intervalo e calcula a média em Java
        List<Ocorrencia> encerradas =
                ocorrRepo.findByStatusAndHoraFimBetween(
                        Ocorrencia.Status.FECHADA, inicioDT, fimDT);

        double mediaMin = encerradas.stream()
                .mapToLong(Ocorrencia::duracaoMinutos)
                .average()
                .orElse(0.0);

        return new RelatorioResumo(totalFocos, totalAlertas, mediaMin);
    }

}
