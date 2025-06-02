package br.com.queimadas.alertas.repository;

import br.com.queimadas.alertas.domain.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório Spring Data JPA para a entidade {@link Alerta}.
 * Fornece consultas específicas para fluxos de trabalho do sistema.
 */
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    /* -------------------------- Consultas customizadas -------------------------- */

    /**
     * Retorna todos os alertas com o status especificado.
     */
    List<Alerta> findByStatus(Alerta.Status status);

    /**
     * Conta quantos alertas foram criados dentro de um intervalo de tempo.
     */
    long countByCriadoEmBetween(LocalDateTime inicio, LocalDateTime fim);
}
