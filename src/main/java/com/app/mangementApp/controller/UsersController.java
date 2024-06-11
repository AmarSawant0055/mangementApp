package com.app.mangementApp.controller;

import com.app.mangementApp.Dto.UserDto;
import com.app.mangementApp.Dto.UserRoleUpdateDto;
import com.app.mangementApp.constants.UserAccountStatusTypes;
import com.app.mangementApp.exceptions.ApplicationException;
import com.app.mangementApp.modal.User;
import com.app.mangementApp.service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService userService;


    @GetMapping("/get/all-pending/associates")
    public ResponseEntity<List<UserDto>> getAllPendingAssociates() {
        List<UserDto> allPendingUsers = this.userService.getAllPendingUsers();
        return new ResponseEntity<>(allPendingUsers, HttpStatus.OK);
    }

    @GetMapping("/get/all-users")
    public ResponseEntity<List<UserDto>> getAllAssociates() {
        List<UserDto> allStudents = this.userService.getAllAssociates();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @PutMapping("/approve/associate")
    public ResponseEntity<Boolean> approvePendingUser(@RequestParam("email") String emailAdd) {
        Boolean status = this.userService.approvePendingUser(emailAdd);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/decline/associate")
    public ResponseEntity<Boolean> declinePendingUser(@RequestParam("email") String emailAdd) {
        Boolean status = this.userService.declinePendingUser(emailAdd);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/all-associates-status")
    public ResponseEntity<List<UserDto>> getAllAssociatesByStatus(@RequestParam("status") String status) {
        List<UserDto> allAssociatesByStatus = this.userService.getAllAssociatesByStatus(status);
        return new ResponseEntity<>(allAssociatesByStatus, HttpStatus.OK);
    }
    @PutMapping("/{emailAddress}")
    public ResponseEntity<User> updateAccountStatusAndRole(@PathVariable String emailAddress,
                                                           @RequestBody UserRoleUpdateDto userRoleUpdateDto) throws ApplicationException {

        User updatedUser = userService.updateAccountStatusAndRole(emailAddress, userRoleUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        try {
            userService.deleteUserByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

}
