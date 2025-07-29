package utez.edu.mx.florever.utils;

import org.springframework.http.HttpStatus;

public class APIResponse<T> {
    private String message;
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}