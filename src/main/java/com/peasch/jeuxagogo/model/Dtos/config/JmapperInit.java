package com.peasch.jeuxagogo.model.Dtos.config;

import com.googlecode.jmapper.JMapper;
import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmapperInit {

    @Bean
    JMapper<UserDto, User> userToDTOMapper() {
        return new JMapper<>(UserDto.class, User.class);
    }

    @Bean
    JMapper<User, UserDto> dtoToUserMapper() {
        return new JMapper<>(User.class, UserDto.class);
    }
}
