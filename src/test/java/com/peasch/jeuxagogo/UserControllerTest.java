package com.peasch.jeuxagogo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
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
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JeuxagogoApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {
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
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/user/all")).andExpect(status().isOk());

    }

    @Test
    void testAddAndUpdateUser() throws Exception {

        List<UserDto> userList = Arrays.asList(
                UserDto.builder().name("bon").firstname("jean").username("jambon")
                        .password("test123").email("jean@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build(),
                UserDto.builder().name("bon").firstname("sameEmail").username("jambon2")
                        .password("test123").email("jean@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build(),
                UserDto.builder().name("bon").firstname("sameUsername").username("jambon")
                        .password("test123").email("jean3@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build(),
                UserDto.builder().name("bon").firstname("falseEmail").username("jambon")
                        .password("test123").email("jean3bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build());

        String jsonRequest = mapper.writeValueAsString(userList.get(0));

        mockMvc.perform(get("/user/username/admin").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/user/username/tom").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());

        MvcResult result = mockMvc.perform(post("/user/add").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        UserDto jambon = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        String jambonToString = mapper.writeValueAsString(jambon);

        for (UserDto userDto : userList) {
            String jsonRequest2 = mapper.writeValueAsString(userDto);
            mockMvc.perform(post("/user/add").content(jsonRequest2)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isForbidden());
        }

        mockMvc.perform(get("/user/delete/" + jambon.getId())
                .content(jambonToString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
//-----------------------------
}
