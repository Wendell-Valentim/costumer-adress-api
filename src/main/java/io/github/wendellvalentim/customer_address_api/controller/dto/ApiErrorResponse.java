package io.github.wendellvalentim.customer_address_api.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiErrorResponse(int status, String mensagem, List<ApiFieldError> erros) {

    public static ApiErrorResponse repostaPadrao(String mensagem) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ApiErrorResponse conflito(String mensagem) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
