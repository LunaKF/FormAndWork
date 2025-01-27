package com.proyecto.FormAndWork.exception;

public class ResourceNotModifiedException extends RuntimeException {
    public ResourceNotModifiedException(String mensaje) {
        super(mensaje);
    }
}
