package com.app.mangementApp.service.Service;

import com.app.mangementApp.security.jwt.JwtAuthRequest;
import com.app.mangementApp.security.jwt.JwtAuthResponse;

public interface IAuthService {
    JwtAuthResponse login(JwtAuthRequest authRequest);

}
