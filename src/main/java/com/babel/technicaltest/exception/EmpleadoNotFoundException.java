package com.babel.technicaltest.exception;

import com.babel.technicaltest.utils.AppConstants;

public class EmpleadoNotFoundException extends RuntimeException {
    public EmpleadoNotFoundException(Long id) {
        super(AppConstants.EMPLEADO_NOT_FOUND.formatted(id));
    }
}
