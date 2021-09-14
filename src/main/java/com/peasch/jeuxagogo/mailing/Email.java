package com.peasch.jeuxagogo.mailing;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Email {
    private String from="peaschaming@gmail.com";
    private List<String> to;
    private String subject;
    private String message;
}
