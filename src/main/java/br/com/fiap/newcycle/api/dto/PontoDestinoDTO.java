package br.com.fiap.newcycle.api.dto;

import br.com.fiap.newcycle.domain.model.PontoDestino.TipoDestino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PontoDestinoDTO(
        Long id,

        @NotBlank(message = "nome é obrigatório")
        @Size(max = 60)
        String nome,

        @NotBlank(message = "endereco é obrigatório")
        @Size(max = 120)
        String endereco,

        @NotNull(message = "tipo é obrigatório")
        TipoDestino tipo,

        @Positive(message = "capacidadeMaxKg deve ser positiva")
        BigDecimal capacidadeMaxKg
) {}
