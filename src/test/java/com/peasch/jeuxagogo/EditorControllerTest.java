package com.peasch.jeuxagogo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import lombok.extern.java.Log;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JeuxagogoApplication.class)
@AutoConfigureMockMvc
@Log
public class EditorControllerTest {

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
    void TEST_GET_EDITORS() throws Exception {
        mockMvc.perform(get("/editor/all")).andExpect(status().isOk());
    }

    @Test
    void ADD_MODIFY_AND_DELETE_EDITOR() throws Exception {
        EditorDto editor = EditorDto.builder().name("Hasbro").build();
        mockMvc.perform(post("/editor/add").content(mapper.writeValueAsString(editor))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
        editor.setCountry("France");
        MvcResult result = mockMvc.perform(post("/editor/add").content(mapper.writeValueAsString(editor))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        editor=mapper.readValue(result.getResponse().getContentAsString(),EditorDto.class);
        editor.setCountry("Etats-Unis");
        mockMvc.perform(post("/editor/add").content(mapper.writeValueAsString(editor))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

        result = mockMvc.perform(put("/editor/update").content(mapper.writeValueAsString(editor))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        editor=mapper.readValue(result.getResponse().getContentAsString(),EditorDto.class);

        mockMvc.perform(delete("/editor/delete/"+editor.getId())).andExpect(status().isOk());
    }
}
