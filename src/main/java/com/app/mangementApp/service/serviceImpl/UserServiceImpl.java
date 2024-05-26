package com.app.mangementApp.service.serviceImpl;

import com.app.mangementApp.modal.User;
import com.app.mangementApp.repository.RoleRepository;
import com.app.mangementApp.repository.UserRepository;
import com.app.mangementApp.service.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public void save(User user) {
//        user.setPass(passwordEncoder.encode(user.getPass()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
