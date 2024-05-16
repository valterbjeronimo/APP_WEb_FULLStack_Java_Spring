package com.tokioFilme.domain.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ChangePasswordDTO extends PasswordDTO {

  private String oldPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
}
