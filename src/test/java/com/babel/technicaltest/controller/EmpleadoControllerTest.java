package com.babel.technicaltest.controller;

import com.babel.technicaltest.dto.EmpleadoRequest;
import com.babel.technicaltest.dto.EmpleadoResponse;
import com.babel.technicaltest.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllEmpleados() throws Exception {
        EmpleadoResponse response = new EmpleadoResponse(
                1L, "Juan", null, "Pérez", null, 30, "M",
                LocalDate.of(1993, 5, 15), "Desarrollador");

        when(empleadoService.getAllEmpleados()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].primerNombre").value("Juan"));
    }

    @Test
    public void testCreateEmpleado() throws Exception {
        EmpleadoRequest request = new EmpleadoRequest(
                "Ana", "María", "Gómez", "Sánchez", 28, "F",
                LocalDate.of(1995, 10, 20), "Diseñadora");

        EmpleadoResponse response = new EmpleadoResponse(
                1L, "Ana", "María", "Gómez", "Sánchez", 28, "F",
                LocalDate.of(1995, 10, 20), "Diseñadora");

        when(empleadoService.createEmpleado(any(EmpleadoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.primerNombre").value("Ana"));
    }

    @Test
    public void testDeleteEmpleado() throws Exception {
        mockMvc.perform(delete("/api/empleados/1"))
                .andExpect(status().isNoContent());
    }
}
