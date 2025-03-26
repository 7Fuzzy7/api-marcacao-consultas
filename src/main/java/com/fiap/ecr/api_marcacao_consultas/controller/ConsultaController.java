package com.fiap.ecr.api_marcacao_consultas.controller;
import com.fiap.ecr.api_marcacao_consultas.model.Consulta;
import com.fiap.ecr.api_marcacao_consultas.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaService consultaService;
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }
    @PostMapping
    public ResponseEntity<Consulta> criarConsulta(@RequestBody Consulta consulta) {
        return ResponseEntity.ok(consultaService.salvarConsulta(consulta));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Consulta>> buscarConsulta(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarConsulta(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable Long id) {
        consultaService.deletarConsulta(id);
        return ResponseEntity.noContent().build();
    }
}