package br.com.queimadas.alertas.controller;

import br.com.queimadas.alertas.domain.entity.AgenteAmbiental;
import br.com.queimadas.alertas.repository.AgenteAmbientalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agentes")
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteAmbientalRepository repo;

    /** Lista todos os agentes. */
    @GetMapping
    public List<AgenteAmbiental> listar() {
        return repo.findAll();
    }

    /** Cadastra um novo agente ou equipe. */
    @PostMapping
    public ResponseEntity<AgenteAmbiental> criar(@RequestBody AgenteAmbiental agente) {
        AgenteAmbiental salvo = repo.save(agente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
