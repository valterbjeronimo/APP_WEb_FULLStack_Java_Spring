package com.tokioFilme.domain.dto;

import com.tokioFilme.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class EditUserDTO {

  @NotBlank(message = "{field.mandatory}")
  private String username;
  @Size(min = 2, message = "{field.name.length}")
  private String name;
  @Size(min = 2, message = "{field.surname.length}")
  private String surname;
  @Email(message = "{field.email.valid}")
  private String email;
  private String image;

  public EditUserDTO(User user) {
    this.username = user.getUsername();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.email = user.getEmail();
    this.image = user.getImage();
  }

  public User map() {
    User user = new User();
    user.setUsername(username);
    user.setName(name);
    user.setSurname(surname);
    user.setEmail(email);
    return user;
  }

  @Override
  public String toString() {
    return "EditUserDTO{" +
      "username='" + username + '\'' +
      ", name='" + name + '\'' +
      ", surname='" + surname + '\'' +
      ", email='" + email + '\'' +
      ", image='" + image + '\'' +
      '}';
  }
}


