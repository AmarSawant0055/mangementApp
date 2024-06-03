package com.app.mangementApp.service.Service;

import com.app.mangementApp.Dto.UserDto;
import com.app.mangementApp.modal.User;

import java.util.List;

/**
 * Project Name - Management Application
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - Amar Sawant
 * @since - 28-05-2024
 */
public interface UserService {

    String createNewUser(UserDto userDto);

    UserDto getUserByEmailAdd(String emailAdd);

    List<UserDto> getAllTrainers();

    List<UserDto> getAllAssociates();

    List<UserDto> getAllPendingUsers();

    Boolean approvePendingUser(String emailAdd);

    Boolean declinePendingUser(String emailAdd);

    List<UserDto> getAllAssociatesByStatus(String status);

    public User updateUserRole(String emailAdd, Long userRoleId);


}
