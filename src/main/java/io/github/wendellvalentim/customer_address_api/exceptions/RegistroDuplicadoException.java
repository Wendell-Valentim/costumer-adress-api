package io.github.wendellvalentim.customer_address_api.exceptions;

public class RegistroDuplicadoException  extends RuntimeException{

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
