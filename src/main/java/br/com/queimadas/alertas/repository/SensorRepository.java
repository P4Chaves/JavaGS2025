package br.com.queimadas.alertas.repository;

import br.com.queimadas.alertas.domain.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório Spring Data para operações CRUD sobre {@link Sensor}.
 * Como {@link Sensor} é abstrato, o Spring/JPA lida automaticamente
 * com suas subclasses {@link br.com.queimadas.alertas.domain.entity.SensorDrone}
 * e {@link br.com.queimadas.alertas.domain.entity.SensorTorre}.
 */
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    // Métodos de consulta adicionais podem ser declarados aqui, se necessário.
}
