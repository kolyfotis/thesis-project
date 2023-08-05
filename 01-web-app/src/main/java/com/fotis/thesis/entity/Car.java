package com.fotis.thesis.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "cars")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Car {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Integer id;


@Column(name = "make")
@NotBlank(message = "Make is mandatory")
private String make;

@Column(name = "model")
@NotBlank(message = "Model is mandatory")
private String model;

@Column(name = "body_type")
@NotBlank(message = "Body Type is mandatory")
private String bodyType;

@Column(name = "fuel_type")
@NotBlank(message = "Fuel Type is mandatory")
private String fuelType;

@Column(name = "price")
@NotBlank(message = "Price is mandatory")
private Double price;

@Column(name = "horsepower")
@Min(value = 0, message = "Horsepower must be positive")
@Max(value = 10_000, message = "Horsepower can't be more than 10.000")
@NotBlank(message = "Horsepower is mandatory")
private Short horsepower;

@Column(name = "gearbox")
@NotBlank(message = "Gearbox is mandatory")
private String gearbox;

@Column(name = "door_number")
@Min(value = 0, message = "Door Number must be positive")
@Max(value = 7, message = "Door Number can't be more than 7")
@NotBlank(message = "Door Number is mandatory")
private Integer doorNumber;

@Column(name = "color")
@NotBlank(message = "Color is mandatory")
private String color;

@Column(name = "image", columnDefinition = "TEXT")
private String image;
}
