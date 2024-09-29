package com.customers.app.utils;

import com.customers.app.utils.pagination.PageResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class ApiResponse<T> {
    private String message;
    private T data;
    private HttpStatus status;
    private String errorMessage;
    private PageResponse<T> page;

    public static ApiResponse<?> status(HttpStatus status) {
        return ApiResponse.builder()
                .status(status)
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.success(message, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success(null, data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.success(message, data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> success(String message, T data, HttpStatus status) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message)
                .status(status)
                .build();
    }

    public static <T> ApiResponse<T> failure(String errorMessage) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(errorMessage)
                .build();
    }

    public static <T> ApiResponse<T> pageResponse(PageResponse<T> page) {
        return ApiResponse.<T>builder()
                .page(page)
                .build();
    }
}
