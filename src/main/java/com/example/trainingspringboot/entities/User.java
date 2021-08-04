package com.example.trainingspringboot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "createddate", nullable = false)
    private LocalDate createdDate = LocalDate.now(ZoneId.of("GMT+07:00"));
    @Column(name = "updateddate", nullable = false)
    private LocalDate updatedDate = LocalDate.now(ZoneId.of("GMT+07:00"));

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;
}
