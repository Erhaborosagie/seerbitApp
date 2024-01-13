package com.osagie.seerbitApp;

import com.osagie.seerbitApp.controllers.TransactionService;
import com.osagie.seerbitApp.models.requests.TransactionRequest;
import com.osagie.seerbitApp.models.response.TransactionStatisticsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceTest {
    @Autowired
    TransactionService service;

    @Test
    public void testPostTransaction(){
        String time = String.valueOf(LocalDateTime.now().minusSeconds(2));
        TransactionRequest request1 = new TransactionRequest("12.3343",time);
        ResponseEntity<?> responseEntity = service.postTransaction(request1);

        System.out.println(responseEntity.getStatusCode().value());

        assert(responseEntity.getStatusCode().value() == 201);

    }

    @Test
    public void testPostTransactionEarlierThan30Secs() {
        String time = String.valueOf(LocalDateTime.now().minusSeconds(31));
        TransactionRequest request1 = new TransactionRequest("12.3343",time);
        ResponseEntity<?> responseEntity = service.postTransaction(request1);

        assert(responseEntity.getStatusCode().value() == 204);
    }

    @Test
    public void testPostTransactionInvalidJson() {
        TransactionRequest request1 = new TransactionRequest();
        ResponseEntity<?> responseEntity = service.postTransaction(request1);

        assert(responseEntity.getStatusCode().value() == 400);
    }

    @Test
    public void testPostTransactionFutureTime(){
        String time = String.valueOf(LocalDateTime.now().plusSeconds(100));
        TransactionRequest request1 = new TransactionRequest("12.3343",time);
        ResponseEntity<?> responseEntity = service.postTransaction(request1);

        assert(responseEntity.getStatusCode().value() == 422);
    }

    @Test
    public void testPostTransactionInvalidTime() {
        TransactionRequest request1 = new TransactionRequest("12.3343","obiIsABoy");
        ResponseEntity<?> responseEntity = service.postTransaction(request1);

        assert(responseEntity.getStatusCode().value() == 422);
    }

    @Test
    public void testPostTransactionInvalidAmount() {
        String time = String.valueOf(LocalDateTime.now().plusSeconds(10));
        TransactionRequest request1 = new TransactionRequest("EzeGoesToSchool",time);
        ResponseEntity<?> responseEntity = service.postTransaction(request1);

        assert(responseEntity.getStatusCode().value() == 422);
    }

    @Test
    public void testDeleteTransaction(){
        ResponseEntity<?> responseEntity = service.deleteTransaction();

        System.out.println(responseEntity.getStatusCode().value());

        assert(responseEntity.getStatusCode().value() == 204);
    }

    @Test
    public void testGetTransactionStatistics(){
        ResponseEntity<TransactionStatisticsResponse> transactionStatistics = service.getTransactionStatistics();

        System.out.println(transactionStatistics.getStatusCode().value());

        assert(transactionStatistics.getStatusCode().value() == 200);
    }
}
