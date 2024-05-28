package com.app.mangementApp.service.Service;

import com.app.mangementApp.Dto.UserRoleDto;

import java.util.Set;


/**
 * Project Name - Management Application
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - Amar Sawant
 * @since - 27-05-2024
 */
public interface IUserRoleService {

    UserRoleDto createUserRole(UserRoleDto userRoleDto);

    UserRoleDto getUserRoleById(Long roleId);

    UserRoleDto getUserRoleByRoleName(String roleName);

    Set<UserRoleDto> getAllUserRoles();

    void roleUpdateScheduler();
}
