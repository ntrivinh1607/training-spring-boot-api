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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name = "id")
    private Integer id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now(ZoneId.of("GMT+07:00"));
    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate = LocalDate.now(ZoneId.of("GMT+07:00"));

    @ManyToOne(fetch = FetchType.EAGER) // for fetch data out session
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;
}
