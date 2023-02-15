package com.oleynikovee.atm.model.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.oleynikovee.atm.model.error.ErrorType.ERROR;
import static com.oleynikovee.atm.model.error.ErrorType.WARNING;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private ErrorType type;
    private ErrorCode code;
    private String message;
    private Object[] textParameters;
    private String field;

    public static ErrorMessage buildWarning(String message) {
        return new ErrorMessage(WARNING, null, message, null, null);
    }

    public static ErrorMessage buildError(ErrorCode code) {
        return new ErrorMessage(ERROR, code, null, null, null);
    }

    public static ErrorMessage buildError(ErrorCode code, String message) {
        return new ErrorMessage(ERROR, code, message, null, null);
    }

    public static ErrorMessage buildError(ErrorCode code, String message, Object[] textParameters) {
        return new ErrorMessage(ERROR, code, message, textParameters, null);
    }

    public static ErrorMessage buildError(ErrorCode code, String message, String field) {
        return new ErrorMessage(ERROR, code, message, null, field);
    }
}
