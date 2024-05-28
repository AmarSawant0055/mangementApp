package com.app.mangementApp.Dto;

import com.app.mangementApp.constants.UserAccountStatusTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String fullName;

    private String emailAdd;

    private String password;

    private String address;

    private Long contactNo;

    private String confirmPassword;

    private UserAccountStatusTypes accountStatus;

    private UserRoleDto userRole;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @JsonProperty
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
