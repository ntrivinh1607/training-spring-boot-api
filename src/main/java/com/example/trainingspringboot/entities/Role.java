package com.example.trainingspringboot.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true, nullable = false, name = "name")
    private String name;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now(ZoneId.of("GMT+07:00"));
    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate = LocalDate.now(ZoneId.of("GMT+07:00"));

    @OneToMany(mappedBy = "role")
    private Collection<User> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<Permission> mappedPermission = new ArrayList<>();
}