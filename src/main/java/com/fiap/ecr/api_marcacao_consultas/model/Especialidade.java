package com.fiap.ecr.api_marcacao_consultas.model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "especialidades")
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}