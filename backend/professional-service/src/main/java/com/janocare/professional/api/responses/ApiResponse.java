package com.janocare.professional.api.responses;

public class ApiResponse<T> {

    public boolean success;

    public String message;

    public T data;

    public ApiResponse() {}

    // =====================================================
    // SUCCESS WITH DATA
    // =====================================================

    public static <T> ApiResponse<T> success(T data) {

        ApiResponse<T> response =
                new ApiResponse<>();

        response.success = true;

        response.data = data;

        response.message = "Success";

        return response;
    }

    // =====================================================
    // SUCCESS WITH DATA + MESSAGE
    // =====================================================

    public static <T> ApiResponse<T> success(
            T data,
            String message
    ) {

        ApiResponse<T> response =
                new ApiResponse<>();

        response.success = true;

        response.data = data;

        response.message = message;

        return response;
    }

    // =====================================================
    // ERROR
    // =====================================================

    public static <T> ApiResponse<T> error(
            String message
    ) {

        ApiResponse<T> response =
                new ApiResponse<>();

        response.success = false;

        response.message = message;

        response.data = null;

        return response;
    }
}