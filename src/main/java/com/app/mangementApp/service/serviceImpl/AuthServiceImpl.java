package com.app.mangementApp.service.serviceImpl;

import com.app.mangementApp.constants.AppConstants;
import com.app.mangementApp.constants.RequestStatusTypes;
import com.app.mangementApp.constants.UserAccountStatusTypes;
import com.app.mangementApp.exceptions.ApplicationException;
import com.app.mangementApp.security.jwt.JwtAuthRequest;
import com.app.mangementApp.security.jwt.JwtAuthResponse;
import com.app.mangementApp.security.jwt.JwtTokenHelper;
import com.app.mangementApp.security.userDetails.CustomDetailsServiceImpl;
import com.app.mangementApp.security.userDetails.CustomUserDetails;
import com.app.mangementApp.service.Service.IAuthService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private JwtTokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomDetailsServiceImpl userDetailsService;

    @Override
    public JwtAuthResponse login(JwtAuthRequest authRequest) {
        String userName = authRequest.getEmail();
        String password = authRequest.getPassword();
        String token = null;

        this.authenticate(userName, password);

        CustomUserDetails userDetails = this
                .userDetailsService
                .loadUserByUsername(userName);

        Assert.notNull(userDetails);
        JwtAuthResponse authResponse = new JwtAuthResponse();

        if (StringUtils.equalsIgnoreCase(userDetails.getAccountStatus().toString(),
                UserAccountStatusTypes.APPROVED.toString())) {

            token = this
                    .tokenHelper
                    .generateToken(userDetails);

            authResponse.setToken(token);
            authResponse.setMessage("Successfully Logged In");
            authResponse.setStatus(RequestStatusTypes.SUCCESS.toString());

        } else if (StringUtils.equalsIgnoreCase(userDetails.getAccountStatus().toString(),
                UserAccountStatusTypes.DECLINED.toString())) {

            authResponse.setToken(null);
            authResponse.setStatus(RequestStatusTypes.FAILED.toString());
            authResponse.setMessage(AppConstants.DECLINED_USER_MESSAGE);
        } else {
            authResponse.setToken(null);
            authResponse.setStatus(RequestStatusTypes.FAILED.toString());
            authResponse.setMessage("Your request is in Pending state for Approval");
        }
        return authResponse;
    }

    private void authenticate(String userName,
                              String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException credentialsException) {
            throw new ApplicationException("Invalid username or password");
        }
    }
}
