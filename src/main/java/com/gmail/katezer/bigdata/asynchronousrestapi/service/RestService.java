package com.gmail.katezer.bigdata.asynchronousrestapi.service;

import com.gmail.katezer.bigdata.asynchronousrestapi.model.SomeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Random;
import java.util.concurrent.Future;

@Service
class RestService implements BaseService {
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Override
    @Async
    public Future<SomeObject> create(int id, String name) throws InterruptedException {
        // this is some long execution method
        Random rnd = new Random();
        long waitTime = 5000 + (long)(rnd.nextDouble()*(10000 - 5000));  // wait at least 5-10 seconds to force asynchronous
        Thread.sleep(waitTime);
        SomeObject so = new SomeObject(id, name, waitTime);
        return new AsyncResult<>(so);
    }
}
