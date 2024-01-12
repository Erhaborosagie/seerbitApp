package com.osagie.seerbitApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osagie.seerbitApp.controllers.TransactionService;
import com.osagie.seerbitApp.models.requests.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SeerbitAppControllerTest {
    @MockBean
    TransactionService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testPostTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest("12.3343","2018-07-17T09:59:51.312Z");
        Mockito.when(service.postTransaction(request)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(
                (MockMvcRequestBuilders.post("/transaction"))
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTransaction() throws Exception {
        Mockito.when(service.getTransaction()).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(
                (MockMvcRequestBuilders.get("/transaction"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Mockito.when(service.deleteTransaction()).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(
                (MockMvcRequestBuilders.delete("/transaction"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }
}
