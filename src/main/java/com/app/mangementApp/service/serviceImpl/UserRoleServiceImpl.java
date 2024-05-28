package com.app.mangementApp.service.serviceImpl;

import com.app.mangementApp.Dto.UserRoleDto;
import com.app.mangementApp.constants.UserRoleTypes;
import com.app.mangementApp.exceptions.ApplicationException;
import com.app.mangementApp.modal.UserRole;
import com.app.mangementApp.repository.UserRoleRepository;
import com.app.mangementApp.service.Service.IUserRoleService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UserRoleDto createUserRole(UserRoleDto userRoleDto) {
        UserRole userRole = null;

        if (ObjectUtils.isNotEmpty(userRoleDto)) {
            userRole = this
                    .modelMapper
                    .map(userRoleDto, UserRole.class);

            userRole = roleRepository
                    .save(userRole);

            userRoleDto = this
                    .modelMapper
                    .map(userRole, UserRoleDto.class);
        } else {
            throw new ApplicationException("User Role Details are empty or null");
        }

        return userRoleDto;
    }

    @Override
    public UserRoleDto getUserRoleById(Long roleId) {
        UserRoleDto userRoleDto = null;
        UserRole userRole = null;

        if (ObjectUtils.isNotEmpty(roleId)) {
            userRole = this
                    .roleRepository
                    .getUserRoleByRoleId(roleId);

            if (ObjectUtils.isNotEmpty(userRole)) {
                userRoleDto = this
                        .modelMapper
                        .map(userRole, UserRoleDto.class);
            } else {
                throw new ApplicationException("User Role not found with the provided Id");
            }
        } else {
            throw new ApplicationException("User Role Id is empty or null");
        }

        return userRoleDto;
    }

    @Override
    public UserRoleDto getUserRoleByRoleName(String roleName) {
        UserRoleDto userRoleDto = null;
        UserRole userRole = null;

        if (StringUtils.isNotEmpty(roleName)) {
            userRole = this
                    .roleRepository
                    .getUserRoleByRoleName(roleName);

            if (ObjectUtils.isNotEmpty(userRole)) {
                userRoleDto = this
                        .modelMapper
                        .map(userRole, UserRoleDto.class);
            } else {
                throw new ApplicationException("User Role not found with the provided role name");
            }
        } else {
            throw new ApplicationException("User Role name is empty or null");
        }

        return userRoleDto;
    }

    @Override
    public Set<UserRoleDto> getAllUserRoles() {
        Set<UserRoleDto> userRolesDto = new HashSet<>();
        Set<UserRole> userRoles = new HashSet<>();

        userRoles = this
                .roleRepository
                .getAllUserRoles();

        if (!userRoles.isEmpty()) {
            userRolesDto = userRoles
                    .stream()
                    .map(ur -> this
                            .modelMapper
                            .map(ur, UserRoleDto.class))
                    .collect(Collectors.toSet());
        }
        return userRolesDto;
    }

    @Override
    @Async
    @Scheduled(initialDelay = 1000, fixedDelay = 1800000)
    public void roleUpdateScheduler() {
        Set<UserRoleDto> allUserRoles = new HashSet<>();
        allUserRoles = this
                .getAllUserRoles();

        if (allUserRoles.isEmpty()) {

            UserRoleDto requesterUserRole = new UserRoleDto(501L, UserRoleTypes.ROLE_ASSOCIATE.toString());
            UserRoleDto trainerUserRole = new UserRoleDto(502L, UserRoleTypes.ROLE_TEACHER.toString());
            UserRoleDto techManagerUserRole = new UserRoleDto(503L, UserRoleTypes.ROLE_ADMIN.toString());

            allUserRoles.add(requesterUserRole);
            allUserRoles.add(trainerUserRole);
            allUserRoles.add(techManagerUserRole);

            allUserRoles.forEach(this :: createUserRole);
        }
    }
}
