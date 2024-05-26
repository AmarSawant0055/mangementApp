package com.app.mangementApp.modal;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

}
