package com.ibizabroker.lms.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "role") // Lowercase for PostgreSQL compatibility
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
}
