package com.gmail.katezer.bigdata.asynchronousrestapi.service;

import com.gmail.katezer.bigdata.asynchronousrestapi.model.SomeObject;

import java.util.concurrent.Future;

public interface BaseService {

    Future<SomeObject> create(int id, String name) throws InterruptedException;
}
