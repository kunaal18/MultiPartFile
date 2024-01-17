package com.tyss.job.portal.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Integer id;

	@NotBlank
	private String userName;

	@NotBlank
	private String email;

	@Size(min = 10)
	@NotBlank
	private Long phoneNumber;

	@NotBlank
	private String education;

	@NotBlank
	private String gender;

	@NotBlank
	private String password;
}
