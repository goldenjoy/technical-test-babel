package com.babel.technicaltest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(
        name = "EmpleadoBulkRequest",
        description = "DTO para crear uno o múltiples empleados."
)
public class EmpleadoBulkRequest {
    @Valid
    @NotNull
    private List<EmpleadoRequest> empleados;
}
