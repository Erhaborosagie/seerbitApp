package com.osagie.seerbitApp.controllers;

import com.osagie.seerbitApp.models.requests.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class SeerbitAppController {
    private final TransactionService service;

    @PostMapping(value = "/transactions", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity postTransactions(@RequestBody TransactionRequest request){
        return service.postTransaction(request);
    }

    @GetMapping(value = "/statistics", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getTransactions(){
        return service.getTransactionStatistics();
    }

    @DeleteMapping(value = "/transactions", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteTransactions(){
        return service.deleteTransaction();
    }
}
