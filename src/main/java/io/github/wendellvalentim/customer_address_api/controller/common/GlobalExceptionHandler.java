package io.github.wendellvalentim.customer_address_api.controller.common;

import io.github.wendellvalentim.customer_address_api.controller.dto.ApiErrorResponse;
import io.github.wendellvalentim.customer_address_api.controller.dto.ApiFieldError;
import io.github.wendellvalentim.customer_address_api.exceptions.RecursoNaoEncontradoException;
import io.github.wendellvalentim.customer_address_api.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.customer_address_api.exceptions.ValidacaoIdadeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErros = e.getFieldErrors();
    List<ApiFieldError> listaErros = fieldErros.stream().map(fe -> new ApiFieldError(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
    return new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ApiErrorResponse.conflito(e.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleRecursoNaoEncontradoException (RecursoNaoEncontradoException e) {
        return ApiErrorResponse.repostaPadrao(e.getMessage());
    }

    @ExceptionHandler(ValidacaoIdadeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleRecursoNaoEncontradoException (ValidacaoIdadeException e) {
        return ApiErrorResponse.repostaPadrao(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleRecursoNaoEncontradoException (AccessDeniedException e) {
        return new ApiErrorResponse( HttpStatus.FORBIDDEN.value(), "Acesso negado!", List.of());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleErrosNaoTratados (RuntimeException e) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro inesperado, contade a admnistração!", List.of());
    }
}
