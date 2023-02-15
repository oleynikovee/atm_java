package com.oleynikovee.atm.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oleynikovee.atm.model.error.ErrorCode;
import com.oleynikovee.atm.model.error.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@NoArgsConstructor
public class ResponseObject<T> {
    private T body;
    private List<ErrorMessage> errors;

    private ResponseObject(ResponseBuilder<T> builder) {
        this.body = builder.body;
        this.errors = builder.errors;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }

    public static class ResponseBuilder<T> {
        private T body;
        private List<ErrorMessage> errors;

        private ResponseBuilder() {
        }

        public ResponseBuilder<T> body(T body) {
            this.body = body;
            return this;
        }

        public ResponseBuilder<T> fail(List<ErrorMessage> errors) {
            if (CollectionUtils.isEmpty(this.errors)) {
                this.errors = new ArrayList<>();
            }
            this.errors.addAll(errors);
            return this;
        }

        public ResponseBuilder<T> fail(ErrorMessage error) {
            if (CollectionUtils.isEmpty(this.errors)) {
                this.errors = new ArrayList<>();
            }
            this.errors.add(error);
            return this;
        }

        public ResponseObject<T> build() {
            return new ResponseObject<>(this);
        }
    }

    public boolean hasError() {
        return CollectionUtils.isNotEmpty(errors);
    }

    public static <T> ResponseObject<T> result(ResponseObject<T> response) {
        return response.hasError()
                ? fail(response)
                : success(response);
    }

    public static <T, R> ResponseObject<R> result(ResponseObject<T> response, Function<T, R> mapper) {
        return response.hasError()
                ? fail(response)
                : success(response, mapper);
    }

    public static <T> ResponseObject<T> result(T body) {
        return ResponseObject.<T>builder()
                .body(body)
                .build();
    }

    public static <T> ResponseObject<T> voidResponse() {
        return ResponseObject.<T>builder()
                .build();
    }

    public static <T, R> ResponseObject<R> fail(ResponseObject<T> response) {
        return ResponseObject.<R>builder()
                .fail(response.getErrors())
                .build();
    }

    public static <T> ResponseObject<T> fail(ErrorCode code) {
        return fail(code, null);
    }

    public static <T> ResponseObject<T> fail(ErrorCode code, String message) {
        return ResponseObject.<T>builder()
                .fail(ErrorMessage.buildError(code, message))
                .build();
    }

    @JsonIgnore
    public int getHttpCode() {
        return errors.iterator().next().getCode().getHttpCode();
    }

    private static <T, R> ResponseObject<R> success(ResponseObject<T> response, Function<T, R> mapper) {
        return ResponseObject.<R>builder()
                .body(mapper.apply(response.getBody()))
                .build();
    }

    private static <T> ResponseObject<T> success(ResponseObject<T> response) {
        return ResponseObject.<T>builder()
                .body(response.getBody())
                .build();
    }
}
