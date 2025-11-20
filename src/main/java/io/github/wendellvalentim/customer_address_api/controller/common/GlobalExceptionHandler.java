package io.github.wendellvalentim.customer_address_api.controller.common;

import io.github.wendellvalentim.customer_address_api.controller.dto.ApiErrorResponse;
import io.github.wendellvalentim.customer_address_api.controller.dto.ApiFieldError;
import org.springframework.http.HttpStatus;
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


}
