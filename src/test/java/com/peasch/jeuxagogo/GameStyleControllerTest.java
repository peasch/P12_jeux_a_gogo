package com.peasch.jeuxagogo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.controller.security.config.CustomUserSecurity;
import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import lombok.extern.java.Log;
import org.apache.cassandra.locator.Ec2Snitch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JeuxagogoApplication.class)
@AutoConfigureMockMvc
@Log
public class GameStyleControllerTest {

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
    void TEST_GET_GAMESTYLE() throws Exception{
        mockMvc.perform(get("/style/all")).andExpect(status().isOk());

    }

    @Test
    void TEST_ADD_AND_DELETE_GAMESTYLE() throws Exception {
        GameStyleDto gameStyleDto = GameStyleDto.builder().name("facile").build();
        String jsonRequest = mapper.writeValueAsString(gameStyleDto);
        MvcResult result = mockMvc.perform(post("/style/add").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        GameStyleDto gameStyleDto1 = mapper.readValue(
                result.getResponse().getContentAsString(),GameStyleDto.class);

        mockMvc.perform(get("/style/"+ gameStyleDto1.getId())).andExpect(status().isOk());
        mockMvc.perform(delete("/style/delete/"+ gameStyleDto1.getId())).andExpect(status().isOk());
    }
}
