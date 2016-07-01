package com.gmail.katezer.bigdata.asynchronousrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aavl on 30/06/2016.
 */
public class PendingObject {
    @JsonProperty("int")
    private final int id;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("url")
    private final String url;

    public PendingObject(int id, String description, String url) {
        this.id = id;
        this.description = description;
        this.url = url;
    }
}
