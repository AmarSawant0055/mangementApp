package com.app.mangementApp.security.jwt;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthResponse {

    private String token;

    private String status;

    private String message;


}
