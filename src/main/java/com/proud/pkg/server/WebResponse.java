package com.proud.pkg.server;

import lombok.Data;

@Data
public class WebResponse {
    private int code;
    private boolean success;
    private String message;
    private Object data;

    private WebResponse() {}

    public static WebResponse failed(String message) {
        return failed(500, message);
    }

    public static WebResponse failed(int code, String message) {
        WebResponse response = new WebResponse();
        response.code = code;
        response.message = message;
        response.success = false;
        return response;
    }

    public static WebResponse success() {
        WebResponse response = new WebResponse();
        response.code = 200;
        response.success = true;
        return response;
    }


    public static WebResponse success(Object data) {
        WebResponse response = success();
        response.data = data;
        return response;
    }

    public static WebResponse success(int code) {
        WebResponse response = success();
        response.code = code;
        return response;
    }

    public static WebResponse success(String message) {
        WebResponse response = success();
        response.message = message;
        return response;
    }

    public static WebResponse success(int code, String message) {
        WebResponse response = success();
        response.code = code;
        response.message = message;
        return response;
    }

    public static WebResponse success(int code, String message, Object data) {
        WebResponse response = success();
        response.code = code;
        response.message = message;
        response.data = data;
        return response;
    }

    public static WebResponse success(int code, Object data) {
        WebResponse response = success();
        response.code = code;
        response.data = data;
        return response;
    }
}
