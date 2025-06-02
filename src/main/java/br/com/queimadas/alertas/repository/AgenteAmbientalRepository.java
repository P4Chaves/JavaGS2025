package br.com.queimadas.alertas.repository;

import br.com.queimadas.alertas.domain.entity.AgenteAmbiental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório JPA para a entidade {@link AgenteAmbiental}.
 * Útil para cadastro e busca de agentes ou equipes.
 */
public interface AgenteAmbientalRepository extends JpaRepository<AgenteAmbiental, Long> {

    /**
     * Busca agentes cujo nome contenha o texto informado (case-insensitive).
     */
    List<AgenteAmbiental> findByNomeContainingIgnoreCase(String fragmento);
}
