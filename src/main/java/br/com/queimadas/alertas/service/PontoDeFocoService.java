package br.com.queimadas.alertas.service;

import br.com.queimadas.alertas.domain.entity.Alerta;
import br.com.queimadas.alertas.domain.entity.PontoDeFoco;
import br.com.queimadas.alertas.domain.factory.AlertaFactory;
import br.com.queimadas.alertas.repository.AlertaRepository;
import br.com.queimadas.alertas.repository.PontoDeFocoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável por registrar pontos de foco e,
 * quando necessário, gerar automaticamente um {@link Alerta}.
 */
@Service
@RequiredArgsConstructor
public class PontoDeFocoService {

    private final PontoDeFocoRepository focoRepo;
    private final AlertaRepository alertaRepo;

    /* -------------------------------------------------------------------- */
    /* Operações principais                                                 */
    /* -------------------------------------------------------------------- */

    /**
     * Registra um novo ponto de foco.
     * Se {@link PontoDeFoco#precisaAlerta()} retornar true,
     * um alerta correspondente é criado via {@link AlertaFactory}.
     *
     * @param foco entidade preenchida (sensor, intensidade)
     * @return ponto de foco salvo (com ID)
     */
    @Transactional
    public PontoDeFoco registrar(PontoDeFoco foco) {
        PontoDeFoco salvo = focoRepo.save(foco);

        if (salvo.precisaAlerta()) {
            Alerta alerta = AlertaFactory.criar(salvo);
            alertaRepo.save(alerta);
        }
        return salvo;
    }

    /** Lista todos os focos registrados. */
    public List<PontoDeFoco> listarTodos() {
        return focoRepo.findAll();
    }

    /**
     * Lista focos dentro de um intervalo de tempo.
     */
    public List<PontoDeFoco> listarEntre(LocalDateTime inicio, LocalDateTime fim) {
        return focoRepo.findByDataHoraBetween(inicio, fim);
    }
}
