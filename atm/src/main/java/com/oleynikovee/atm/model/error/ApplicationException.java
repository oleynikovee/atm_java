package com.oleynikovee.atm.model.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public static ApplicationException notFound(String objectType) {
        return ApplicationException.builder()
                .errorCode(ErrorCode.ITEM_NOT_FOUND)
                .message(String.format("%s not found!", objectType))
                .build();
    }

    public static ApplicationException badRequest(String message) {
        return ApplicationException.builder()
                .errorCode(ErrorCode.BAD_REQUEST)
                .message(message)
                .build();
    }

    public static ApplicationException systemError(String msg) {
        return ApplicationException.builder()
                .errorCode(ErrorCode.TECHNICAL_ERROR)
                .message(msg)
                .build();
    }
}
