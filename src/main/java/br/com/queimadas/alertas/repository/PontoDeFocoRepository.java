package br.com.queimadas.alertas.repository;

import br.com.queimadas.alertas.domain.entity.PontoDeFoco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * Repositório para operações de persistência em {@link PontoDeFoco}.
 * Inclui consultas que serão úteis nos relatórios.
 */
public interface PontoDeFocoRepository extends JpaRepository<PontoDeFoco, Long> {

    /**
     * Conta quantos focos ocorreram dentro de um intervalo de tempo.
     */
    long countByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Recupera todos os focos dentro de um intervalo de tempo.
     */
    java.util.List<PontoDeFoco> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
