
package com.proyecto.FormAndWork.exception;
/**
 * Excepción personalizada para manejar errores de acceso no autorizado.
 * Esta excepción se lanza cuando un usuario intenta acceder a un recurso
 * sin tener los permisos adecuados.
 */
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String mensaje) {
        super(mensaje);
    }
}