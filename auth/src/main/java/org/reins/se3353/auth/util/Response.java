package org.reins.se3353.auth.util;

import lombok.Data;

@Data
public class Response {
    private String code;
    private Object data;
    private String message;

    public Response(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
