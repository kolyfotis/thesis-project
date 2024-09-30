package com.fotis.thesis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_recommendations")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@IdClass(CompositeKey.class)
public class UserRecommendation {

@Id
@Column(name = "username")
private String username;

@Id
@Column(name = "car_id")
private Integer carId;

@Column(name = "relevance")
private BigDecimal relevance;

}
