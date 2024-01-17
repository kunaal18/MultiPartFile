package com.tyss.job.portal.service;

import java.util.List;

import com.tyss.job.portal.dto.JobDto;
import com.tyss.job.portal.dto.LoginDto;
import com.tyss.job.portal.dto.UserDto;

public interface UserService {

	UserDto login(LoginDto loginDto);

	UserDto register(UserDto userDto);

	JobDto postJobs(JobDto postDto);

	List<JobDto> searchJobs(String jobtitle, String loction);

	List<JobDto> getAllJobs();

}
