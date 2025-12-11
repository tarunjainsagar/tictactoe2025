package org.example.tictactoe.dtos;

public class ValidatorResponse {

    private ResponseStatus status;

    private String message;

    public ValidatorResponse() {
    }

    public ValidatorResponse(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
