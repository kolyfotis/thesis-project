package com.fotis.thesis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

@Id
@Column(name = "username")
private String username;

@Column(name = "password")
private String password;

@Column(name = "enabled")
private Boolean enabled;

}
