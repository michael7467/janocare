package com.janocare.auth.api.responses;

public class SuccessResponse<T> {
    public boolean success;
    public T data;

    public SuccessResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
