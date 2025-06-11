package br.com.fiap.newcycle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AlertaDTO(
        Long id,

        @NotBlank(message = "Mensagem é obrigatória")
        @Size(max = 140)
        String mensagem,

        @NotNull(message = "Data/hora é obrigatória")
        LocalDateTime dataHora,

        @NotNull(message = "pontoColetaId é obrigatório")
        Long pontoColetaId
) {}
