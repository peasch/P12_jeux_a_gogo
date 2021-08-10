package com.peasch.jeuxagogo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.model.dtos.AdviceDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.Advice;
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
public class AdviceCtrlrTest {
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
    void TEST_GET_ADVICES() throws Exception {
        mockMvc.perform(get("/advice/all")).andExpect(status().isOk());

    }
    @Test
    void TEST_GET_ADVICES_BY_GAME_ID() throws Exception {
        mockMvc.perform(get("/advice/all/1")).andExpect(status().isOk());

    }

    @Test
    public void ADD_ADVICE_TEST() throws Exception {

        MvcResult userResult = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        UserDto user = mapper.readValue(userResult.getResponse().getContentAsString(), UserDto.class);
        MvcResult gameResult = mockMvc.perform(get("/game/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        GameDto game = mapper.readValue(gameResult.getResponse().getContentAsString(),GameDto.class);
        AdviceDto adviceDto = AdviceDto.builder().game(game).user(user).rating(5).commentary("trop génial").build();
       String jsonRequest = mapper.writeValueAsString(adviceDto);
        MvcResult result = mockMvc.perform(post("/advice/add").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();


        MvcResult user2Result = mockMvc.perform(get("/user/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        UserDto user2 = mapper.readValue(user2Result.getResponse().getContentAsString(), UserDto.class);


        AdviceDto adviceDto2 = AdviceDto.builder().game(game).user(user2).rating(2).commentary("trop génial").build();
        String jsonRequest2 = mapper.writeValueAsString(adviceDto2);
        MvcResult result2 = mockMvc.perform(post("/advice/add").content(jsonRequest2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        adviceDto=mapper.readValue(result.getResponse().getContentAsString(),AdviceDto.class);
        adviceDto2=mapper.readValue(result2.getResponse().getContentAsString(),AdviceDto.class);

        MvcResult intResult= mockMvc.perform(get("/advice/rating/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        System.out.println(mapper.readValue(intResult.getResponse().getContentAsString(),float.class));
        mockMvc.perform(post("/advice/add").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isForbidden());
        mockMvc.perform(delete("/advice/delete/"+adviceDto.getId()).content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(delete("/advice/delete/"+adviceDto2.getId()).content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }
}
