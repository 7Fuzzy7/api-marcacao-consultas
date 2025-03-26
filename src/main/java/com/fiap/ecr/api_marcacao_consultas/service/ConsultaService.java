package com.fiap.ecr.api_marcacao_consultas.service;
import com.fiap.ecr.api_marcacao_consultas.model.Consulta;
import com.fiap.ecr.api_marcacao_consultas.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }
    public Consulta salvarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }
    public Optional<Consulta> buscarConsulta(Long id) {
        return consultaRepository.findById(id);
    }
    public List<Consulta> listarConsultasPorUsuario(Long usuarioId) {
        return consultaRepository.findByUsuarioId(usuarioId);
    }
    public void deletarConsulta(Long id) {
        consultaRepository.deleteById(id);
    }
}