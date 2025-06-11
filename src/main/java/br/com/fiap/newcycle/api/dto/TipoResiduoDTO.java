package br.com.fiap.newcycle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoResiduoDTO(
        Long id,

        @NotBlank(message = "nome é obrigatório")
        String nome,

        @Size(max = 120)
        String descricao,

        /* Se não enviado, assume false */
        Boolean perigoso
) {}
