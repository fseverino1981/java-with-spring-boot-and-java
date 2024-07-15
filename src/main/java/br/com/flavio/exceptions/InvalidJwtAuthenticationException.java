package br.com.flavio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = -3294683239169388881L;

    public InvalidJwtAuthenticationException(String ex) {
        super(ex);
    }
}
