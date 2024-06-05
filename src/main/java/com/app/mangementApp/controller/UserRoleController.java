package com.app.mangementApp.controller;

import com.app.mangementApp.Dto.UserRoleDto;
import com.app.mangementApp.exceptions.ApplicationException;
import com.app.mangementApp.modal.UserRole;
import com.app.mangementApp.service.Service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

    @Autowired
    IUserRoleService iUserRoleService;

    @PostMapping("/createRole")
    public ResponseEntity<UserRoleDto> createRole(@RequestBody UserRoleDto userRoleDto) throws ApplicationException {
        UserRoleDto createdRoleDto = iUserRoleService.createUserRole(userRoleDto);
        return ResponseEntity.ok(createdRoleDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Set<UserRoleDto>> getAllRoles() {
        Set<UserRoleDto> roles = iUserRoleService.getAllUserRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }



}
