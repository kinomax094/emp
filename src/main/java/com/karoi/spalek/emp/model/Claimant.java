package com.karoi.spalek.emp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "claimant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Claimant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "identifier", unique = true, nullable = false)
    private String identifier;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
