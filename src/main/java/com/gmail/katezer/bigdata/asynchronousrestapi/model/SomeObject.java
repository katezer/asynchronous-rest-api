package com.gmail.katezer.bigdata.asynchronousrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aavl on 30/06/2016.
 */
public class SomeObject {
    @JsonProperty("int")
    private final int id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("time")
    private final long time;

    public SomeObject(int id, String name, long time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }
}
