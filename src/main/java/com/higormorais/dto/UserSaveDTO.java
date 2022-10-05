package com.higormorais.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.higormorais.domain.User;

public class UserSaveDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Você não pode inserir valor nulo.")
	@NotBlank(message = "Você não pode inserir um valor vázio.")
	private String name;
	
	@Email(message = "Email inválido")
	private String email;
	
	// -------------------------------------------------------------------------------------------------------------
	
	public UserSaveDTO() {}
	
	public UserSaveDTO(User userEntity) {
		name = userEntity.getName();
		email = userEntity.getEmail();
	}
	
	// -------------------------------------------------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
