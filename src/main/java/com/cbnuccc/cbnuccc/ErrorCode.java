package com.cbnuccc.cbnuccc;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public enum ErrorCode {
    NO_ERROR(HttpStatus.OK, "There is no error.", -1),
    SOMETHING_WENT_WRONG(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.", 0),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "Inputted email is duplicated.", 1),
    NO_USER_FOUND(HttpStatus.NOT_FOUND, "Cannot found matched user.", 2),
    CONNOT_CHANGE_IMPORTANT_INFORMATION(HttpStatus.FORBIDDEN, "Cannot change user's important information.", 3);

    private final HttpStatusCode responseStatus;
    private final String errorMessage;
    private final int errorCode;

    ErrorCode(HttpStatusCode status, String message, int code) {
        this.responseStatus = status;
        this.errorMessage = message;
        this.errorCode = code;
    }

    public ResponseEntity<?> makeErrorResponseEntity() {
        return ResponseEntity.status(responseStatus).body(Map.of(
                "errorCode", errorCode,
                "message", errorMessage));
    }
}
