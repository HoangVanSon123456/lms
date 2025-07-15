package com.example.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole {
    @Id
    private Integer userId;

    @Id
    private Integer roleId;

}
