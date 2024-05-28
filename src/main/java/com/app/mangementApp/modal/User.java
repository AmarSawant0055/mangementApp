package com.app.mangementApp.modal;

import com.app.mangementApp.constants.UserAccountStatusTypes;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Id
    @Column(name = "email_add")
    private String emailAdd;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "contactNo")
    private Long contactNo;

    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private UserAccountStatusTypes accountStatus;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private UserRole userRole;

}
