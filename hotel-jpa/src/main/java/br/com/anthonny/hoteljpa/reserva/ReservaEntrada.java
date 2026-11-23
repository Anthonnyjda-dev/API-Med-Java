package br.com.anthonny.hoteljpa.reserva;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ReservaEntrada(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Size(max = 30) String sexo,
        @NotBlank @Email @Size(max = 255) String email,
        @NotNull LocalDate dataChegada,
        @NotNull @Positive Integer numeroNoites,
        @NotNull @Positive Integer numeroHospedes,
        @Size(max = 2000) String mensagem) {}
