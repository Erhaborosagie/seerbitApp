package com.osagie.seerbitApp.controllers;

import com.osagie.seerbitApp.models.Transaction;
import com.osagie.seerbitApp.models.requests.TransactionRequest;
import com.osagie.seerbitApp.models.response.TransactionStatisticsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TransactionService {

    private final List<Transaction> transactionList;

    public TransactionService() {
        this.transactionList = new ArrayList<>();
    }

    public ResponseEntity<?> postTransaction(TransactionRequest request) {

        int requestValidation = validateAndSaveRequest(request);

        return ResponseEntity.status(requestValidation).build();


    }

    private int validateAndSaveRequest(TransactionRequest request) {
        if (null == request.getAmount() || null == request.getTimestamp()) return 400;

        LocalDateTime dateTime;
        double amount;
        try {
            dateTime = LocalDateTime.parse(request.getTimestamp());
            amount = Double.parseDouble(request.getAmount());
        }catch (DateTimeParseException | NumberFormatException e){
            return 422;
        }
        if (dateTime.isAfter(LocalDateTime.now())) return 422;
        if (LocalDateTime.now().minusSeconds(30).isAfter(dateTime)) return 204;

        Transaction transaction = new Transaction(amount, dateTime);
        transactionList.add(transaction);

        return 201;
    }

    public ResponseEntity<TransactionStatisticsResponse> getTransactionStatistics() {
        DoubleSummaryStatistics stats = transactionList.stream()
                .filter(transaction -> transaction.getDateTime().plusSeconds(30).isAfter(LocalDateTime.now()))
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(Transaction::getAmount)
                .summaryStatistics();

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.CEILING);

        String average = df.format(stats.getAverage());
        long count = stats.getCount();
        String max = df.format(stats.getMax());
        String min = df.format(stats.getMin());
        String sum = df.format(stats.getSum());

        TransactionStatisticsResponse transactionStatisticsResponse = new TransactionStatisticsResponse(sum, average, max, min, count);
        return ResponseEntity.ok(transactionStatisticsResponse);
    }

    public ResponseEntity<?> deleteTransaction() {
        transactionList.clear();

        return ResponseEntity.status(204).build();
    }
}
