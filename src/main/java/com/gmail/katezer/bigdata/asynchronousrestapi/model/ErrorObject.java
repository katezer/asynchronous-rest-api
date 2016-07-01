package com.gmail.katezer.bigdata.asynchronousrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

/**
 * Created by aavl on 30/06/2016.
 */
public class ErrorObject {
    @JsonProperty("int")
    private final int id;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("code")
    private final HttpStatus code;

    public ErrorObject(int id, String description, HttpStatus code) {
        this.id = id;
        this.description = description;
        this.code = code;
    }
}
