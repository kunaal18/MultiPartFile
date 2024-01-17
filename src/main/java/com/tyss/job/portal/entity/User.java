package com.tyss.job.portal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(value = Include.NON_DEFAULT)
//@JsonIgnoreProperties(value = { "id" })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userName;
	private String email;
	private Long phoneNumber;
	private String education;
	private String gender;
	private String password;

}
