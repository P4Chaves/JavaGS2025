package br.com.queimadas.alertas.service;

import br.com.queimadas.alertas.domain.entity.Ocorrencia;
import br.com.queimadas.alertas.repository.OcorrenciaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por acompanhar e encerrar ocorrências geradas a partir de alertas.
 */
@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrRepo;

    /* -------------------------------------------------------------------- */
    /* Consultas                                                            */
    /* -------------------------------------------------------------------- */

    /** Lista todas as ocorrências (abertas e fechadas). */
    public List<Ocorrencia> listarTodas() {
        return ocorrRepo.findAll();
    }

    /** Recupera uma ocorrência por ID. */
    public Ocorrencia buscarPorId(Long id) {
        return ocorrRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ocorrência não encontrada: " + id));
    }

    /* -------------------------------------------------------------------- */
    /* Encerramento                                                         */
    /* -------------------------------------------------------------------- */

    /**
     * Encerra a ocorrência informada, registrando as ações tomadas em campo.
     * @param idOcorrencia ID da ocorrência
     * @param acoesTexto   descrição livre das ações executadas
     * @return Ocorrência atualizada
     */
    @Transactional
    public Ocorrencia encerrar(Long idOcorrencia, String acoesTexto) {
        Ocorrencia ocorr = buscarPorId(idOcorrencia);
        ocorr.encerrar(acoesTexto);
        return ocorrRepo.save(ocorr);
    }
}
