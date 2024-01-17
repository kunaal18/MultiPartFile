package com.tyss.job.portal.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

	@NotBlank(message = "should not be null")
	private String jobTitle;

	@NotBlank(message = "should not be null")
	private String experience;

	@NotBlank(message = "should not be null")
	private String skillsSet;

	@NotBlank(message = "should not be null")
	private String location;

//	@Pattern(regexp = "^\\d{10}$")
//	@Range(min = 10)
	private long mobileNo;

	@NotBlank(message = "should not be null")
	private String description;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobDto other = (JobDto) obj;
		return Objects.equals(description, other.description) && Objects.equals(experience, other.experience)
				&& Objects.equals(jobTitle, other.jobTitle) && Objects.equals(location, other.location)
				&& mobileNo == other.mobileNo && Objects.equals(skillsSet, other.skillsSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, experience, jobTitle, location, mobileNo, skillsSet);
	}

}
