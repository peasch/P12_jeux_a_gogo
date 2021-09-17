package com.peasch.jeuxagogo.model.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NewPassword {
    private String password;
    private String confirmPassword;
}
