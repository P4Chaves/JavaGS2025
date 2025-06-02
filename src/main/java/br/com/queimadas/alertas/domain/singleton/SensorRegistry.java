package br.com.queimadas.alertas.domain.singleton;

import br.com.queimadas.alertas.domain.entity.Sensor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro global (Singleton) que mantém um cache de {@link Sensor} ativos
 * em memória — útil para consultas rápidas sem bater sempre no banco.
 *
 * • read-heavy: operações de leitura são lock-free graças ao ConcurrentHashMap.  
 * • write: chamadas explícitas de registrar/desativar/remover.
 */
public final class SensorRegistry {

    /* -------------------------------------------------------------------- */
    /* Singleton — instância única                                          */
    /* -------------------------------------------------------------------- */
    private static final SensorRegistry INSTANCE = new SensorRegistry();

    private SensorRegistry() { /* construtor privado */ }

    /** Acesso global à instância única */
    public static SensorRegistry getInstance() {
        return INSTANCE;
    }

    /* -------------------------------------------------------------------- */
    /* Estrutura de armazenamento                                           */
    /* -------------------------------------------------------------------- */
    private final Map<Long, Sensor> sensores = new ConcurrentHashMap<>();

    /* -------------------------------------------------------------------- */
    /* Operações do Registry                                                */
    /* -------------------------------------------------------------------- */

    /** Adiciona ou atualiza um sensor no cache */
    public void registrar(Sensor sensor) {
        if (sensor == null || sensor.getId() == null) {
            throw new IllegalArgumentException("Sensor ou ID nulo");
        }
        sensores.put(sensor.getId(), sensor);
    }

    /** Remove um sensor do cache (por ID) */
    public void remover(Long id) {
        sensores.remove(id);
    }

    /** Obtém um sensor pelo ID (ou null se não existir) */
    public Sensor obter(Long id) {
        return sensores.get(id);
    }

    /** Retorna todos os sensores atualmente registrados */
    public Collection<Sensor> listarTodos() {
        return sensores.values();
    }
}
