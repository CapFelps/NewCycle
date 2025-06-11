package br.com.fiap.newcycle.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResiduoDTO(
        Long id,

        @Size(max = 120, message = "descricao pode ter no máximo 120 caracteres")
        String descricao,

        @NotNull(message = "pesoKg é obrigatório")
        @Positive(message = "pesoKg deve ser maior que zero")
        BigDecimal pesoKg,

        LocalDate dataDeposito,

        @NotNull(message = "tipoResiduoId é obrigatório")
        Long tipoResiduoId,

        @NotNull(message = "pontoColetaId é obrigatório")
        Long pontoColetaId,

        Long coletaId
) {}
