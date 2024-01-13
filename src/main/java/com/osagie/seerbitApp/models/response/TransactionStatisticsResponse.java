package com.osagie.seerbitApp.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatisticsResponse {
    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;

}
