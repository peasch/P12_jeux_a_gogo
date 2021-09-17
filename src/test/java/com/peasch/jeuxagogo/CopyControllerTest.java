package com.peasch.jeuxagogo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.controller.security.config.CustomUserSecurity;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import lombok.extern.java.Log;
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
public class CopyControllerTest {

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
    void TEST_ALL_COPIES() throws Exception {
        mockMvc.perform(get("/copy/all")).andExpect(status().isOk());
    }

    @Test
    void TEST_ALL_COPY_OF_GAME_ID() throws Exception{
        mockMvc.perform(get("/copy/game/1")).andExpect(status().isOk());
    }

    @Test
    void UPDATE_COPY_TEST() throws Exception {

        GameDto game = GameDto.builder().id(1).name("Splendor").build();

        CopyDto copy =CopyDto.builder().available(true).code("dfsgdfg").game(game).build();
        String jsonRequest = mapper.writeValueAsString(copy);
        MvcResult result = mockMvc.perform(post("/copy/add/1").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        copy=mapper.readValue(result.getResponse().getContentAsString(),CopyDto.class);
        copy.setCode("tjhtyjtyj");
        copy.setGame(game);
        jsonRequest = mapper.writeValueAsString(copy);
        mockMvc.perform(put("/copy/update").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        mockMvc.perform(delete("/copy/delete/"+copy.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
    }
}
