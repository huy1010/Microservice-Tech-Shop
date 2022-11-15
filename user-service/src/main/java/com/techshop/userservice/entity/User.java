package com.techshop.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techshop.userservice.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "techshop_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 50)
    private String username;

    @JsonIgnore
    @NotNull
    private String password;

    private String firstName;
    private String lastName;
    private String phoneNo;
    private String address;
    private String imgUrl;
    private String dateOfBirth;


    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "varchar(1) default 'Y'")
    private String activeFlag = "Y";

//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;



}
