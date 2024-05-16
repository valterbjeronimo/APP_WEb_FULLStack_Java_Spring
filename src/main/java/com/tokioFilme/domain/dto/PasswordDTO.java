package com.tokioFilme.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public abstract class PasswordDTO {

  @NotBlank(message = "{field.mandatory}")
  @Size(min= 8, message = "{field.password.length}")
  protected String password;
  @NotNull(message = "{field.password.match}")
  protected String confirmPassword;

  public void setPassword(String password) {
    this.password = password;
    confirmPassword();
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    confirmPassword();
  }

  private void confirmPassword() {
    if (this.password == null || this.confirmPassword == null) {
      return;
    }
    else if (!this.password.equals(this.confirmPassword)) {
      this.confirmPassword = null;
    }
  }


}
