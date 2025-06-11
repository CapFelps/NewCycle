package br.com.fiap.newcycle.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ColetaDTO(
        Long id,

        @NotNull(message = "dataAgendada é obrigatória")
        @FutureOrPresent(message = "dataAgendada não pode estar no passado")
        LocalDate dataAgendada,

        LocalDate dataRealizada,

        @Pattern(regexp = "Agendada|Realizada|Atrasada",
                message = "status deve ser Agendada, Realizada ou Atrasada")
        @NotBlank(message = "status é obrigatório")
        String status,          // “Agendada”, “Realizada”, “Atrasada”

        @Pattern(regexp = "Nenhuma|Semanal|Quinzenal|Mensal",
                message = "frequencia inválida")
        @NotBlank(message = "frequencia é obrigatória")
        String frequencia,      // “Nenhuma”, “Semanal”, “Quinzenal”, “Mensal”

        @NotNull(message = "pontoColetaId é obrigatório")
        Long pontoColetaId
) {}
