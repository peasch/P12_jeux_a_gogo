package com.peasch.jeuxagogo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peasch.jeuxagogo.controller.JeuxagogoApplication;
import com.peasch.jeuxagogo.controller.security.config.CustomUserSecurity;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void TEST_GET_USERS() throws Exception {
        mockMvc.perform(get("/user/all")).andExpect(status().isOk());

    }

    @Test
    void TEST_ADD_AND_UPDATE_USER() throws Exception {

        Set<RoleDto> rolesDto = new HashSet<>();
        RoleDto role = new RoleDto();
        role.setRole("USER");
        role.setId(3);
        rolesDto.add(role);
        List<UserDto> userList = Arrays.asList(
                UserDto.builder().name("bon").firstname("jean").username("jambon").rolesDto(rolesDto)
                        .password("test123").email("jean@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build(),
                UserDto.builder().name("bon").firstname("sameEmail").username("jambon2").rolesDto(rolesDto)
                        .password("test123").email("jean@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build(),
                UserDto.builder().name("bon").firstname("sameUsername").username("jambon").rolesDto(rolesDto)
                        .password("test123").email("jean3@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build(),
                UserDto.builder().name("bon").firstname("falseEmail").username("jambon").rolesDto(rolesDto)
                        .password("test123").email("jean3bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build());

        String jsonRequest = mapper.writeValueAsString(userList.get(0));

        mockMvc.perform(get("/user/username/admin").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mockMvc.perform(get("/user/username/tom").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());

        MvcResult result = mockMvc.perform(post("/user/add").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        UserDto jambon = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        jambon.setUsername("jambonjambon");
        String jambonToString = mapper.writeValueAsString(jambon);

        MvcResult result2 = mockMvc.perform(post("/user/update").content(jambonToString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        UserDto jambonjambon = mapper.readValue(result2.getResponse().getContentAsString(), UserDto.class);
        String jambonjambon2 = mapper.writeValueAsString(jambonjambon);
        MvcResult result3 = mockMvc.perform(put("/user/addRole/2").content(jambonjambon2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        UserDto jambonjambon3 = mapper.readValue(result3.getResponse().getContentAsString(), UserDto.class);
        jambonjambon3.setUsername("jambon");
        jambonToString= mapper.writeValueAsString(jambonjambon3);
        mockMvc.perform(post("/user/update").content(jambonToString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        mockMvc.perform(get("/user/userWithRoles/jambon").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());


        for (UserDto userDto : userList) {
            String jsonRequest2 = mapper.writeValueAsString(userDto);
            mockMvc.perform(post("/user/add").content(jsonRequest2)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isForbidden());
        }

        mockMvc.perform(delete("/user/delete/" + jambon.getId())
                .content(jambonToString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void TEST_USER_MAPPER_IGNORE() throws Exception {
    UserDto user =UserDto.builder().username("admin").build();
    String jsonRequest = mapper.writeValueAsString(user);
      MvcResult result =  mockMvc.perform(get("/user/username/admin").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
      UserDto user2 = mapper.readValue(result.getResponse().getContentAsString(),UserDto.class);
        System.out.println(user2.getGodfather());
    }

    @Test
    void AUTEHNTICATION_AND_LOGIN_TEST() throws Exception {
        UserDto user =UserDto.builder().name("bon").firstname("jean").username("jambon")
                .password("test123").email("jean@bon.fr").birthDate((LocalDate.parse("2021-05-19"))).build();
        String jsonRequest = mapper.writeValueAsString(user);
        mockMvc.perform(post("/auth/register")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        mockMvc.perform(post("/auth/login")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String jsonRequest2 = mapper.writeValueAsString(user);
        mockMvc.perform(delete("/user/delete/username/jambon")
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }



}
