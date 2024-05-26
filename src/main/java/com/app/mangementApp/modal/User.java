package com.app.mangementApp.modal;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String pass;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean enabled;

}
