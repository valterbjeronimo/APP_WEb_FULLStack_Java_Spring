package com.tokioFilme.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "people")
@Table(indexes = @Index(name = "firstname_lastname_index", columnList = "forename, surname"))
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotEmpty(message = "{field.mandatory}")
  @Column(name = "forename", nullable = false)
  private String name;
  @NotEmpty(message = "{field.mandatory}")
  @Column(nullable = false)
  private String surname;
  @NotNull(message = "{field.mandatory}")
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PersonTypeEnum type;

  @Override
  public String toString() {
    return name + " " + surname;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (name != null ? !name.equals(person.name) : person.name != null) return false;
    if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
    return type == person.type;
  }

  @Override
  public int hashCode() {
    int result = (10 ^ (10 >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (surname != null ? surname.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}
