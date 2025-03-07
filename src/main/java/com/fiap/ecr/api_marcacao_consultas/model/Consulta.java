package com.fiap.ecr.api_marcacao_consultas.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHora;
    private String especialidade;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}