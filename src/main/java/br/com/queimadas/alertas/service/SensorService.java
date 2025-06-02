package br.com.queimadas.alertas.service;

import br.com.queimadas.alertas.domain.entity.Sensor;
import br.com.queimadas.alertas.domain.singleton.SensorRegistry;
import br.com.queimadas.alertas.repository.SensorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço para operações de negócio sobre {@link Sensor}.
 * Toda alteração persistida também atualiza o {@link SensorRegistry}.
 */
@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepo;
    private final SensorRegistry registry = SensorRegistry.getInstance();

    /* -------------------------------------------------------------------- */
    /* CRUD básico                                                          */
    /* -------------------------------------------------------------------- */

    /** Lista todos os sensores cadastrados. */
    public List<Sensor> listarTodos() {
        return sensorRepo.findAll();
    }

    /**
     * Salva (insere ou atualiza) um sensor e sincroniza o cache.
     * @param sensor entidade a persistir
     * @return sensor salvo com ID
     */
    @Transactional
    public Sensor salvar(Sensor sensor) {
        Sensor salvo = sensorRepo.save(sensor);
        registry.registrar(salvo);
        return salvo;
    }

    /** Desativa um sensor logicamente e remove do cache. */
    @Transactional
    public void desativar(Long id) {
        Sensor sensor = sensorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor não encontrado: " + id));
        sensor.setAtivo(false);
        registry.remover(id);
    }

    /** Remove fisicamente um sensor do banco e do cache. */
    @Transactional
    public void excluir(Long id) {
        sensorRepo.deleteById(id);
        registry.remover(id);
    }
}
