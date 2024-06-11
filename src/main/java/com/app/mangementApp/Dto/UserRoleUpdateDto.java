package com.app.mangementApp.Dto;


import com.app.mangementApp.constants.UserAccountStatusTypes;
import com.app.mangementApp.modal.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRoleUpdateDto {

    private UserAccountStatusTypes accountStatus;
    private String role;
}
