package br.com.queimadas.alertas.controller;

import br.com.queimadas.alertas.domain.entity.Sensor;
import br.com.queimadas.alertas.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para cadastro e gerenciamento de {@link Sensor}.
 *
 * Rotas principais:
 * • GET    /sensores                 – lista todos os sensores
 * • POST   /sensores                 – cadastra um novo sensor
 * • PUT    /sensores/{id}/desativar  – desativa logicamente
 * • DELETE /sensores/{id}            – remove fisicamente
 */
@RestController
@RequestMapping("/sensores")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService service;

    /* -------------------------------------------------------------------- */
    /* Endpoints                                                             */
    /* -------------------------------------------------------------------- */

    /** Lista todos os sensores cadastrados. */
    @GetMapping
    public List<Sensor> listar() {
        return service.listarTodos();
    }

    /** Cadastra (ou atualiza) um sensor. */
    @PostMapping
    public ResponseEntity<Sensor> criar(@RequestBody Sensor sensor) {
        Sensor salvo = service.salvar(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /** Desativa logicamente um sensor existente. */
    @PutMapping("/{id}/desativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long id) {
        service.desativar(id);
    }

    /** Remove fisicamente um sensor. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
