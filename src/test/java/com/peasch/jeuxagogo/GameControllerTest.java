package com.peasch.jeuxagogo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
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
public class GameControllerTest {

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
    void TESTGETGAMES() throws Exception {
        mockMvc.perform(get("/game/all")).andExpect(status().isOk());

    }

    @Test
    void INCOMPLETE_GAME_ADDING() throws Exception {
        GameDto game = GameDto.builder().available(true).build();
        log.info(mapper.writeValueAsString(game));
        String jsonRequest = mapper.writeValueAsString(game);

        mockMvc.perform(post("/game/add").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isForbidden());

    }

    @Test
    void ADD_AND_UPDATE_AND_DELETE_GAME() throws Exception {
        GameDto game = GameDto.builder().name("cluedo").ageMin(3).available(true).rulesLink("dfsdgsg")
                .minPlayers(2).maxPlayers(6).available(true).build();
        game.setEditorDto(mapper.readValue(mockMvc.perform(get("/editor/1"))
                .andReturn().getResponse().getContentAsString(), EditorDto.class));

        MvcResult result = mockMvc.perform(post("/game/add").content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        mockMvc.perform(post("/game/add").content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isForbidden());
        GameDto gameToUpdate = mapper.readValue(result.getResponse().getContentAsString(), GameDto.class);

        gameToUpdate.setMinPlayers(3);
        CopyDto copy = CopyDto.builder().available(true).code("dfsgdfg").game(gameToUpdate).build();

        mockMvc.perform(post("/copy/add/" + gameToUpdate.getId()).content(mapper.writeValueAsString(copy))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(post("/copy/add/" + gameToUpdate.getId()).content(mapper.writeValueAsString(copy))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isForbidden());
        mockMvc.perform(put("/game/update").content(mapper.writeValueAsString(gameToUpdate))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        mockMvc.perform(delete("/game/delete/" + gameToUpdate.getId())).andExpect(status().isOk());

    }

    @Test
    void SHOW_FIELDS_OF_GAME() throws Exception {
        MvcResult result = mockMvc.perform(get("/game/id/1")).andExpect(status().isOk()).andReturn();
        GameDto game = mapper.readValue(result.getResponse().getContentAsString(), GameDto.class);
        System.out.println(game.getCopiesDto().isEmpty());

        System.out.println(game.getGameStyleDto().getName());
    }

}
