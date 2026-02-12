package com.cbnuccc.cbnuccc;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

public enum ErrorCode {
    // Error codes
    NO_ERROR(HttpStatus.OK, "There is no error.", -1),
    SOMETHING_WENT_WRONG(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.", 0),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "Inputted email is duplicated.", 1),
    NO_USER_FOUND(HttpStatus.NOT_FOUND, "Cannot found matched user.", 2),
    CONNOT_CHANGE_IMPORTANT_INFORMATION(HttpStatus.FORBIDDEN, "Cannot change user's important information.", 3),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Given jwt token is invalid.", 4);

    @Getter
    private final HttpStatusCode responseStatus;

    @Getter
    private final String errorMessage;

    @Getter
    private final int errorCode;

    ErrorCode(HttpStatusCode status, String message, int code) {
        this.responseStatus = status;
        this.errorMessage = message;
        this.errorCode = code;
    }

    // make response entity of when returning an error.
    public ResponseEntity<?> makeErrorResponseEntity() {
        return ResponseEntity.status(responseStatus).body(Map.of(
                "errorCode", errorCode,
                "message", errorMessage));
    }
}
