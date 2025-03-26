package com.fiap.ecr.api_marcacao_consultas.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}