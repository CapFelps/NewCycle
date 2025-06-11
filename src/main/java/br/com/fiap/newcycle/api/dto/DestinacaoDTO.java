package br.com.fiap.newcycle.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DestinacaoDTO(
        Long id,

        LocalDate dataEnvio,

        LocalDate dataRecebimento,

        @Positive(message = "pesoKg deve ser maior que zero")
        BigDecimal pesoKg,

        @NotNull(message = "pontoDestinoId é obrigatório")
        Long pontoDestinoId
) {}
