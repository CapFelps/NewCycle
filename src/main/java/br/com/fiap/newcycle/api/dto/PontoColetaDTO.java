package br.com.fiap.newcycle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;

public record PontoColetaDTO(
        Long id,

        @NotBlank(message = "nome é obrigatório")
        @Size(max = 60)
        String nome,

        @NotBlank(message = "localizacao é obrigatória")
        @Size(max = 120)
        String localizacao,

        @NotNull(message = "limitePesoKg é obrigatório")
        @Positive(message = "limitePesoKg deve ser maior que zero")
        BigDecimal limitePesoKg,

        BigDecimal totalResiduoKg,
        BigDecimal totalColetadoKg
) {}


