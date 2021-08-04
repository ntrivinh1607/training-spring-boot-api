package com.example.trainingspringboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<RolePermission> rolePermissions = new ArrayList<>();
}
