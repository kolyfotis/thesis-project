package com.fotis.thesis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user_data_normalized")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataNormalized {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Integer id;

@Column(name = "username")
private String username;

@Column(name = "field_name")
private String fieldName;

@Column(name = "field_value")
private String fieldValue;

@Column(name = "time_spent")
private BigDecimal timeSpent;

}