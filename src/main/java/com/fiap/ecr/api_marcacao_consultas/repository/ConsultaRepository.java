package com.fiap.ecr.api_marcacao_consultas.repository;
import com.fiap.ecr.api_marcacao_consultas.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByUsuarioId(Long usuarioId);
}