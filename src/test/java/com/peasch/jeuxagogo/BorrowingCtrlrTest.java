package com.peasch.jeuxagogo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JeuxagogoApplication.class)
@AutoConfigureMockMvc
public class BorrowingCtrlrTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void TEST_GET_BORROWINGS() throws Exception {
        mockMvc.perform(get("/borrowing/all")).andExpect(status().isOk());

    }

    @Test
    void TEST_ADD_VALID_RETURN_AND_DELETE_BORROWING() throws Exception {

       MvcResult result = mockMvc.perform(post("/borrowing/add/1").content("admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        BorrowingDto borrowingDto = mapper.readValue(result.getResponse().getContentAsString(),BorrowingDto.class);
        int id = borrowingDto.getId();
        mockMvc.perform(get("/borrowing/valid/" + id).content("admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/return/" + id).content("admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(delete("/borrowing/delete/" + id).content("admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    void TEST_ALL_GET_BORROWINGS() throws Exception {
        mockMvc.perform(get("/borrowing/admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/returned/admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/unreturned/admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/pending/admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/returned")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/unreturned")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/borrowing/allPending")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }
}
