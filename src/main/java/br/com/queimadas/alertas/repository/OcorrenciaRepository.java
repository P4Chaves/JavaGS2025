package br.com.queimadas.alertas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.queimadas.alertas.domain.entity.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    long countByHoraInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    //  <<<  Removido o método tempoMedioRespostaMin  >>>
    List<Ocorrencia> findByStatusAndHoraFimBetween(
            Ocorrencia.Status status,
            LocalDateTime inicio,
            LocalDateTime fim);
}
