package com.babel.technicaltest.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class EmpleadoRequest {
    @NotBlank(message = "El primer nombre es obligatorio")
    private String primerNombre;

    private String segundoNombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apellidoPaterno;

    private String apellidoMaterno;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad mínima es 18")
    private Integer edad;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "[MFO]", message = "El sexo debe ser 'M' (Masculino), 'F' (Femenino) o 'O' (No Binario)")
    private String sexo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El puesto es obligatorio")
    private String puesto;
}
