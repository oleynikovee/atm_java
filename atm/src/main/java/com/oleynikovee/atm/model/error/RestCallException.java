package com.oleynikovee.atm.model.error;

import com.oleynikovee.atm.model.core.ResponseObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestCallException extends RuntimeException {
    private final transient ResponseObject<?> response;
}
