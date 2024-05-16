package com.tokioFilme.domain.dto;

import com.tokioFilme.domain.Role;
import com.tokioFilme.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO extends PasswordDTO {

  @NotBlank(message = "{field.mandatory}")
  private String username;
  private String name;
  private String surname;
  @Email(message = "{field.email.valid}")
  private String email;
  private Set<Role> roles = new HashSet<>();

  private String birthDate;

  public User buildUser() {
    User user = new User();
    user.setUsername(this.username);
    user.setPassword(this.password);
    user.setName(this.name);
    user.setSurname(this.surname);
    user.setBirthDate(birthDate.isEmpty() ? null : LocalDate.parse(this.birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    user.setEmail(this.email);
    roles.forEach(user::addRole);
    return user;
  }
}
