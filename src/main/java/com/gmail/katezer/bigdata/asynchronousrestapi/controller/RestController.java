package com.gmail.katezer.bigdata.asynchronousrestapi.controller;

import com.gmail.katezer.bigdata.asynchronousrestapi.service.BaseService;
import com.gmail.katezer.bigdata.asynchronousrestapi.model.ErrorObject;
import com.gmail.katezer.bigdata.asynchronousrestapi.model.PendingObject;
import com.gmail.katezer.bigdata.asynchronousrestapi.model.SomeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.concurrent.Future;

@org.springframework.web.bind.annotation.RestController
class RestController {
    private static final Logger log = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private BaseService restService;

    private HashMap<Integer, Future<SomeObject>> futureObjects = new HashMap<>();

    @RequestMapping("/create")
    DeferredResult<ResponseEntity<?>> create(@RequestParam("id") int id, @RequestParam("name") String name)
            throws InterruptedException {
        System.out.println("Starting creation: " + id);
        long start = System.currentTimeMillis();
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        Future<SomeObject> so = restService.create(id, name);                                                           // call some method that has a long execution time, returns a future
        futureObjects.put(id, so);                                                                                      // put the future in the HashMap
        PendingObject po = new PendingObject(
                id
                , "Object under creation, please consult on the referenced URL"
                , "/get?id=" + id);
        ResponseEntity<PendingObject> responseEntity = new ResponseEntity<>(po, HttpStatus.ACCEPTED);                   // returns pendingObject indicating how to poll the status
        deferredResult.setResult(responseEntity);
        System.out.println("Create Elapsed time: " + (System.currentTimeMillis() - start));
        return deferredResult;
    }

    @RequestMapping("/get")
    DeferredResult<ResponseEntity<?>> get(@RequestParam("id") int id) throws InterruptedException {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        try {
            Future<SomeObject> futureSO = futureObjects.get(id);                                                            // get future from HashMap
            if (futureSO.isDone()) {                                                                                         // has the future ended?
                // future ended, get and return the result
                SomeObject so = futureSO.get();
                ResponseEntity<SomeObject> responseEntity = new ResponseEntity<>(so, HttpStatus.CREATED);               // returns someObject, the actual result
                deferredResult.setResult(responseEntity);
            } else {
                // future still pending, return same pending as before
                PendingObject po = new PendingObject(
                        id
                        , "Object still under creation, please consult on the referenced URL"
                        , "/get?id=" + id);
                ResponseEntity<PendingObject> responseEntity = new ResponseEntity<>(po, HttpStatus.ACCEPTED);               // returns pendingObject, same as create method
                deferredResult.setResult(responseEntity);
            }
        } catch (Exception e) {
            // error, return errorObject
            ErrorObject po = new ErrorObject(
                    id
                    , e.getMessage()
                    , HttpStatus.I_AM_A_TEAPOT);
            ResponseEntity<ErrorObject> responseEntity = new ResponseEntity<>(po, HttpStatus.I_AM_A_TEAPOT);        // :) returns errorObject, same as create method
            deferredResult.setResult(responseEntity);
        }
        return deferredResult;
    }
}
