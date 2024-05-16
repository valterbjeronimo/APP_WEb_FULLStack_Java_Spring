package com.tokioFilme.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Table
@Entity(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "role", nullable = false)
  private String name;

}
