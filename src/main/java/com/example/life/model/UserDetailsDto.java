package com.example.life.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String username;
    private String email;
    private String password;
}
