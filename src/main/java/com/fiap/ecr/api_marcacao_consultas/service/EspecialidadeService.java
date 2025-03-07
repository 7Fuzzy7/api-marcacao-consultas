package com.fiap.ecr.api_marcacao_consultas.service;
import com.fiap.ecr.api_marcacao_consultas.model.Especialidade;
import com.fiap.ecr.api_marcacao_consultas.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;
    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }
    public List<Especialidade> listarEspecialidades() {
        return especialidadeRepository.findAll();
    }
}