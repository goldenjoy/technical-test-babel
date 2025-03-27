package com.babel.technicaltest.service;

import com.babel.technicaltest.dto.EmpleadoRequest;
import com.babel.technicaltest.dto.EmpleadoResponse;
import com.babel.technicaltest.exception.EmpleadoNotFoundException;
import com.babel.technicaltest.model.Empleado;
import com.babel.technicaltest.repository.EmpleadoRepository;
import com.babel.technicaltest.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public List<EmpleadoResponse> getAllEmpleados() {
        log.debug(AppConstants.MSG_API_ALL_EMPLEADOS);
        return empleadoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteEmpleado(Long id) {
        log.debug(AppConstants.MSG_API_DELETE_EMPLEADO.formatted(id));
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNotFoundException(id);
        }
        empleadoRepository.deleteById(id);
    }

    @Transactional
    public EmpleadoResponse updateEmpleado(Long id, EmpleadoRequest request) {
        log.debug(AppConstants.MSG_API_UPDATE_EMPLEADO);
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNotFoundException(id));

        updateEntityFromRequest(empleado, request);
        Empleado updatedEmpleado = empleadoRepository.save(empleado);
        return mapToResponse(updatedEmpleado);
    }

    @Transactional
    public List<EmpleadoResponse> createMultipleEmpleados(List<EmpleadoRequest> requests) {
        log.debug(AppConstants.MSG_API_ADD_EMPLEADOS);
        List<Empleado> empleados = requests.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        List<Empleado> savedEmpleados = empleadoRepository.saveAll(empleados);
        return savedEmpleados.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmpleadoResponse createEmpleado(EmpleadoRequest request) {
        Empleado empleado = mapToEntity(request);
        Empleado savedEmpleado = empleadoRepository.save(empleado);
        return mapToResponse(savedEmpleado);
    }

    // Mappers
    private Empleado mapToEntity(EmpleadoRequest request) {
        return Empleado.builder()
                .primerNombre(request.getPrimerNombre())
                .segundoNombre(request.getSegundoNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .edad(request.getEdad())
                .sexo(request.getSexo())
                .fechaNacimiento(request.getFechaNacimiento())
                .puesto(request.getPuesto())
                .build();
    }

    private void updateEntityFromRequest(Empleado empleado, EmpleadoRequest request) {
        empleado.setPrimerNombre(request.getPrimerNombre());
        empleado.setSegundoNombre(request.getSegundoNombre());
        empleado.setApellidoPaterno(request.getApellidoPaterno());
        empleado.setApellidoMaterno(request.getApellidoMaterno());
        empleado.setEdad(request.getEdad());
        empleado.setSexo(request.getSexo());
        empleado.setFechaNacimiento(request.getFechaNacimiento());
        empleado.setPuesto(request.getPuesto());
    }

    private EmpleadoResponse mapToResponse(Empleado empleado) {
        return EmpleadoResponse.builder()
                .id(empleado.getId())
                .primerNombre(empleado.getPrimerNombre())
                .segundoNombre(empleado.getSegundoNombre())
                .apellidoPaterno(empleado.getApellidoPaterno())
                .apellidoMaterno(empleado.getApellidoMaterno())
                .edad(empleado.getEdad())
                .sexo(empleado.getSexo())
                .fechaNacimiento(empleado.getFechaNacimiento())
                .puesto(empleado.getPuesto())
                .build();
    }
}
