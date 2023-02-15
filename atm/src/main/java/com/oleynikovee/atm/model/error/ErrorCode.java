package com.oleynikovee.atm.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ErrorCode {
    UNDEFINED(500),
    TECHNICAL_ERROR(500),
    BAD_REQUEST(400),
    UNKNOWN_LANGUAGE(400),
    INPUT_VALIDATION(400),
    SERVICE_UNAVAILABLE(503),
    PAYLOAD_TOO_LARGE(413),
    INCORRECT_BENEFIT_TYPE(400),
    INCORRECT_BENEFIT_KIND(400),
    INCORRECT_ROLE(400),
    HOLDER_ID_IS_NULL(400),
    MULTIPLE_PRIMARY_ADDRESSES(400),
    USER_NOT_FOUND(404),
    USER_DATA_NOT_AVAILABLE(422),
    FINANCIAL_DATA_NOT_FOUND(404),
    PERSONAL_DATA_NOT_FOUND(404),
    PROVIDER_NOT_FOUND(404),
    PROVIDER_ALREADY_EXISTS(409),
    ADDRESS_NOT_FOUND(404),
    FORBIDDEN(403),
    MEDIA_TYPE_NOT_SUPPORTED(400),
    BENEFIT_REGISTRY_NOT_FOUND(404),
    BENEFIT_REGISTRY_ALREADY_EXISTS(409),
    BENEFIT_ALREADY_ASSIGNED(409),
    CUSTOMER_ALREADY_EXISTS(409),
    BENEFIT_NOT_FOUND(404),
    BENEFIT_ALREADY_EXISTS(409),
    VIEW_NOT_FOUND(404),
    ITEM_NOT_FOUND(404),
    VIEW_ALREADY_EXISTS(409),
    ITEM_ALREADY_EXISTS(409),
    PROVIDER_ID_IS_NULL(400),
    BENEFIT_DAY_OF_WEEK_ALREADY_EXISTS(409),
    BENEFIT_DAY_OF_WEEK_NOT_FOUND(404),
    ADDRESS_ALREADY_EXISTS(409),
    CONTACT_ALREADY_EXISTS(409),
    SETTING_ALREADY_EXISTS(409),
    USER_SETTING_ALREADY_EXISTS(409),
    CUSTOMER_SETTING_NOT_FOUND(404),
    USER_SETTING_NOT_FOUND(404),
    CONTACT_NOT_FOUND(404),
    MENU_NOT_FOUND(404),
    LOCATION_NOT_FOUND(404),
    PLACE_ID_IS_NULL(400),
    PLACE_DETAILS_NOT_FOUND(400),
    PRIVATE_PENSION_NOT_FOUND(404),
    ALREADY_ASSIGNED(409),
    USER_ID_IS_NULL(400),
    FORMAT_ERROR(400),
    PRIVATE_PENSION_ALREADY_EXISTS(409),
    EMPLOYER_PENSION_ALREADY_EXISTS(409),
    DEFERRED_COMPENSATION_ALREADY_EXISTS(409),
    DEFERRED_COMPENSATION_NOT_FOUND(404),
    EMPLOYER_PENSION_NOT_FOUND(404),
    PENSION_CALCULATOR_ERROR(400),
    EMAIL_NOT_SENT(400),


    ENROLLMENT_NOT_FOUND(404),
    ORDER_NOT_FOUND(404),
    USER_ALREADY_EXISTS(409),
    CAN_NOT_CREATE_USER(400),
    CAN_NOT_CREATE_ENROLLMENT(400),
    CAN_NOT_CREATE_ORDER(400),
    SIGNATURE_INVALID(401),

    LESSON_NF(404);

    private static final Map<String, ErrorCode> container = new HashMap<>();

    static {
        Arrays.stream(values())
                .forEach(errorCode -> container.put(errorCode.getName(), errorCode));
    }

    private final int httpCode;

    ErrorCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    @JsonValue
    public String getName() {
        return this.name();
    }

    @JsonIgnore
    public static Optional<ErrorCode> getByName(String name) {
        return Optional.ofNullable(container.get(name));
    }
}
