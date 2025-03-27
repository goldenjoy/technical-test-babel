package com.babel.technicaltest.controller;

import com.babel.technicaltest.dto.EmpleadoBulkRequest;
import com.babel.technicaltest.dto.EmpleadoRequest;
import com.babel.technicaltest.dto.EmpleadoResponse;
import com.babel.technicaltest.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Tag(name = "Empleados API", description = "Operaciones CRUD para gestión de empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    /**
     * API que recupera el listado de todos los empleados registrados.
     * @return Lista de empleados en formato JSON.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los empleados")
    public ResponseEntity<List<EmpleadoResponse>> getAllEmpleados() {
        return ResponseEntity.ok(empleadoService.getAllEmpleados());
    }

    /**
     * API que borra un empleado por su ID.
     * @param id ID del empleado a eliminar.
     * @return Respuesta HTTP sin contenido (204 No Content).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado por ID")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * API que actualiza cualquier dato de un empleado existente.
     * @param id ID del empleado a actualizar.
     * @param request Datos actualizados del empleado.
     * @return Empleado actualizado en formato JSON.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado por ID")
    public ResponseEntity<EmpleadoResponse> updateEmpleado(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(empleadoService.updateEmpleado(id, request));
    }

    /**
     * API que inserta uno o varios empleados en una sola petición.
     * @param request Lista de empleados a crear.
     * @return Lista de empleados creados en formato JSON.
     */
    @PostMapping()
    @Operation(summary = "Registra uno o múltiples empleados")
    public ResponseEntity<?> createEmpleados(@Valid @RequestBody EmpleadoBulkRequest request) {
        if (request.getEmpleados().size() > 1) {
            return ResponseEntity.ok(empleadoService.createMultipleEmpleados(request.getEmpleados()));
        } else if (request.getEmpleados().size() == 1) {
            return ResponseEntity.ok(List.of(empleadoService.createEmpleado(request.getEmpleados().get(0))));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar al menos un empleado");
        }
    }
}
