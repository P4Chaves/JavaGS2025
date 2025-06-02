package br.com.queimadas.alertas.domain.vo;

/**
 * Value Object imutável que representa o resumo estatístico
 * retornado pelo endpoint de relatórios.
 *
 * • totalFocos            – número de pontos de foco detectados  
 * • totalAlertas          – quantidade de alertas gerados  
 * • tempoMedioRespostaMin – média de minutos entre abertura e encerramento das ocorrências
 *
 * Como Java 17 permite records, o VO é enxuto e “read-only”.
 */
public record RelatorioResumo(
        long totalFocos,
        long totalAlertas,
        double tempoMedioRespostaMin
) { }
