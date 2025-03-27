package com.babel.technicaltest.service;

import com.babel.technicaltest.dto.EmpleadoRequest;
import com.babel.technicaltest.dto.EmpleadoResponse;
import com.babel.technicaltest.exception.EmpleadoNotFoundException;
import com.babel.technicaltest.model.Empleado;
import com.babel.technicaltest.repository.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    public void testGetAllEmpleados() {
        // Datos de prueba
        Empleado empleado = Empleado.builder()
                .id(1L)
                .primerNombre("Juan")
                .apellidoPaterno("Pérez")
                .edad(30)
                .sexo("M")
                .fechaNacimiento(LocalDate.of(1993, 5, 15))
                .puesto("Desarrollador")
                .build();

        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        // Ejecución
        List<EmpleadoResponse> result = empleadoService.getAllEmpleados();

        // Verificación
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getPrimerNombre());
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmpleadoById_NotFound() {
        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmpleadoNotFoundException.class, () -> {
            empleadoService.getEmpleadoById(1L);
        });
    }

    @Test
    public void testCreateEmpleado() {
        EmpleadoRequest request = new EmpleadoRequest(
                "Ana", "María", "Gómez", "Sánchez", 28, "F",
                LocalDate.of(1995, 10, 20), "Diseñadora");

        Empleado empleadoGuardado = Empleado.builder()
                .id(1L)
                .primerNombre("Ana")
                .apellidoPaterno("Gómez")
                .edad(28)
                .sexo("F")
                .fechaNacimiento(LocalDate.of(1995, 10, 20))
                .puesto("Diseñadora")
                .build();

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);

        EmpleadoResponse response = empleadoService.createEmpleado(request);

        assertEquals("Ana", response.getPrimerNombre());
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }
}
