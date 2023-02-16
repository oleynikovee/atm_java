package com.oleynikovee.atm.core;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseHeader {
    HttpHeaders httpHeaders;

    public static ResponseHeadersBuilder builder() {
        return new ResponseHeadersBuilder();
    }

    public static class ResponseHeadersBuilder {

        private final HttpHeaders httpHeaders = new HttpHeaders();

        public ResponseHeadersBuilder contentDisposition(String fileName) {
            this.httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resolveFileName(fileName));
            return this;
        }

        public ResponseHeadersBuilder contentType(MediaType mediaType) {
            this.httpHeaders.add(HttpHeaders.CONTENT_TYPE, mediaType.toString());
            return this;
        }

        public String resolveFileName(String fileName) {
            return isNoneBlank(fileName)
                           ? fileName
                           : "NO_NAME";
        }

        public ResponseHeader build() {
            return new ResponseHeader(httpHeaders);
        }
    }
}
