package com.fiap.ecr.api_marcacao_consultas.controller;
import com.fiap.ecr.api_marcacao_consultas.model.Especialidade;
import com.fiap.ecr.api_marcacao_consultas.service.EspecialidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {
    private final EspecialidadeService especialidadeService;
    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }
    @GetMapping
    public ResponseEntity<List<Especialidade>> listarEspecialidades() {
        return ResponseEntity.ok(especialidadeService.listarEspecialidades());
    }
}