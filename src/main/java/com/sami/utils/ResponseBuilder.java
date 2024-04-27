package com.sami.utils;

import com.sami.exception.ApiError;
import lombok.Data;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseBuilder implements Response {

    private final ResponseType responseType;

    private Object data;

    private Object errors;

    private Map<String, Object> meta;

    private Map<String, Object> dataMap;

    private ApiError apiError;

    private ResponseBuilder(ResponseType responseType) {
        this.responseType = responseType;
    }

    public static Response success(Object data) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.DATA);
        response.data = data;
        return response;
    }

    public static Response errors(Object errors) {
        return error(errors);
    }

    public static Response error(ApiError error) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.ERROR);
        response.apiError = error;
        return response;
    }

    public static ResponseBuilder error(Object errors) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.ERROR);
        response.errors = errors;
        return response;
    }

    @Override
    public JSONObject getJson() {
        Map<String, Object> map = new HashMap<>();
        switch (this.responseType) {
            case DATA:
                map.put("data", data);
                break;
            case ERROR:
                map.put("errors", apiError);
                map.put("error", errors);
        }
        return new JSONObject(map);
    }
}
