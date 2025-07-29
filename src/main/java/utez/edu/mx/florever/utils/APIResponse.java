package utez.edu.mx.florever.utils;

import org.springframework.http.HttpStatus;

public class APIResponse<T> {
    private String message;
    private T data;
    private boolean error;
    private HttpStatus status;

    public APIResponse(HttpStatus status, boolean error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public APIResponse(String message, HttpStatus status, boolean error, T data) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}