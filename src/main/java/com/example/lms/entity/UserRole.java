package com.example.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    private Integer userId;

    @Id
    private Integer roleId;

}
