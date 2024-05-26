package com.app.mangementApp.service.Service;

import com.app.mangementApp.modal.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
