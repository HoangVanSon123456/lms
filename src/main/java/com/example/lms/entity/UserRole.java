package com.example.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    private Integer userId;

    @Id
    private Integer roleId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;
}
