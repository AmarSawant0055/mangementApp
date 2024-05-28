package com.app.mangementApp.service.serviceImpl;

import com.app.mangementApp.Dto.UserDto;
import com.app.mangementApp.Dto.UserRoleDto;
import com.app.mangementApp.constants.AppConstants;
import com.app.mangementApp.constants.UserAccountStatusTypes;
import com.app.mangementApp.constants.UserRoleTypes;
import com.app.mangementApp.exceptions.ApplicationException;
import com.app.mangementApp.modal.User;
import com.app.mangementApp.repository.UserRepository;
import com.app.mangementApp.service.Service.IUserRoleService;
import com.app.mangementApp.service.Service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public String createNewUser(UserDto userDto) {
        User user = null;
        String message = null;
        if (ObjectUtils.isNotEmpty(userDto)) {

            user = this
                    .userRepository
                    .getUserByEmail(userDto.getEmailAdd());

            if (ObjectUtils.isEmpty(user)) {
                //user do not exist, new user will be created
                if (StringUtils.equals(userDto.getPassword(), userDto.getConfirmPassword())) {

                    if (ObjectUtils.isNotEmpty(userDto.getId())) {
                        UserRoleDto userRoleDto = this
                                .userRoleService
                                .getUserRoleByRoleName(UserRoleTypes.ROLE_ASSOCIATE.toString());

                        userDto.setUserRole(userRoleDto);

                        user = this
                                .modelMapper
                                .map(userDto, User.class);

                        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
                        user.setAccountStatus(UserAccountStatusTypes.PENDING);

                        //reassigned with the new created data
                        user = this
                                .userRepository
                                .save(user);

                        if (ObjectUtils.isNotEmpty(user))
                            message = AppConstants.NEW_USER_REGISTRATION_SUCCESS_MESSAGE;
                        else
                            message = "User creation failed";
                    } else
                        throw new ApplicationException("Student Id is empty or null, please enter valid student id");
                } else
                    throw new ApplicationException("Password did not matched, please try again");
            } else
                throw new ApplicationException("User already exists with this email address");
        } else
            throw new ApplicationException("Invalid user details");

        return message;
    }

    @Override
    public UserDto getUserByEmailAdd(String emailAdd) {
        UserDto userDto = null;
        User user = null;

        if (StringUtils.isNotEmpty(emailAdd)) {
            user = this.userRepository.getUserByEmail(emailAdd);

            userDto = this
                    .modelMapper
                    .map(user, UserDto.class);
        } else {
            throw new ApplicationException("Email add is empty");
        }
        return userDto;
    }

    @Override
    public List<UserDto> getAllTrainers() {
        List<User> allTrainers = this.userRepository.findAllUsersByRole(UserRoleTypes.ROLE_TEACHER.toString());
        if (!allTrainers.isEmpty()) {
            return allTrainers
                    .stream()
                    .map(yur -> this
                            .modelMapper
                            .map(yur, UserDto.class))
                    .collect(Collectors.toList());
        } else
            throw new ApplicationException("No Trainers found !");
    }

    @Override
    public List<UserDto> getAllAssociates() {
        List<User> allTrainers = this.userRepository.findAllUsersByRole(UserRoleTypes.ROLE_ASSOCIATE.toString());
        if (!allTrainers.isEmpty()) {
            return allTrainers
                    .stream()
                    .map(yur -> this
                            .modelMapper
                            .map(yur, UserDto.class))
                    .collect(Collectors.toList());
        } else
            throw new ApplicationException("No Associates found !");
    }

    @Override
    public List<UserDto> getAllPendingUsers() {
        List<User> pendingUsers = this.userRepository.getAllPendingUsers();
        return pendingUsers
                .stream()
                .map(penUser -> this
                        .modelMapper
                        .map(penUser, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Boolean approvePendingUser(String emailAdd) {
        if (StringUtils.isNotEmpty(emailAdd)) {
            Integer status = this.userRepository.approvePendingUser(emailAdd);
            if (status == 1)
                return true;
            else
                throw new ApplicationException("User not approved");
        } else {
            throw new ApplicationException("Email address is empty.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Boolean declinePendingUser(String emailAdd) {
        if (StringUtils.isNotEmpty(emailAdd)) {
            Integer status = this.userRepository.declinePendingUser(emailAdd);
            if (status == 1)
                return true;
            else
                throw new ApplicationException("Failed while declining user account");
        } else {
            throw new ApplicationException("Email address is empty.");
        }
    }

    @Override
    public List<UserDto> getAllAssociatesByStatus(String status) {
        List<UserDto> yotaUserDto = null;
        List<User> user = null;
        if (StringUtils.isNotEmpty(status)) {
            UserAccountStatusTypes userAccountStatusTypes = UserAccountStatusTypes.valueOf(status);
            user = this.userRepository.getAllUserByStatus(userAccountStatusTypes);
            if (CollectionUtils.isEmpty(user)) {
                throw new ApplicationException("No associates found with the provided status : " + status);
            }

            yotaUserDto = user.stream().filter(users-> users.getUserRole().getRoleTypes().equals(UserRoleTypes.ROLE_ASSOCIATE.toString()))
                    .map(users-> modelMapper.map(users, UserDto.class))
                    .collect(Collectors.toList());
        } else {
            throw new ApplicationException("Status is empty");
        }
        return yotaUserDto;
    }




}
