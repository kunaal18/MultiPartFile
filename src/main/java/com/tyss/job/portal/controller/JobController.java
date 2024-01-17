package com.tyss.job.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.job.portal.constants.JobConstants;
import com.tyss.job.portal.dto.JobDto;
import com.tyss.job.portal.dto.LoginDto;
import com.tyss.job.portal.dto.ResponseDto;
import com.tyss.job.portal.dto.UserDto;
import com.tyss.job.portal.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;

@RestController
@RequestMapping("/api/v1")
@OpenAPIDefinition(info = @Info(title = "JOB PORTAL", description = "SEARCH AND POST JOBS", version = "V1"))
public class JobController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	@Operation(summary = "Register User API")
	@Description(value = "Register YourSelf To Get Jobs")

	public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDto) {
		return ResponseEntity
				.ok(new ResponseDto(false, JobConstants.USER_SUCCESSFULLY_REGISTERED, userService.register(userDto))); 

	}

	@PostMapping("/login")
	@Operation(summary = "Login User API")
	public ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto) {

		return ResponseEntity.ok(new ResponseDto(false, JobConstants.LOGIN_SUCCESSFULL, userService.login(loginDto)));

	}

	@PostMapping("/postJob")
	@Operation(summary = "Post Job API")
	public ResponseEntity<ResponseDto> postJobs(@RequestBody JobDto jobDto) {
		return ResponseEntity.ok(new ResponseDto(false, JobConstants.JOB_POSTED, userService.postJobs(jobDto)));

	}

	@GetMapping("/job")
	@Operation(summary = "Search Job API")
	public ResponseEntity<ResponseDto> searchJobs(@RequestParam String jobtitle,
			@RequestParam @Parameter(description = "Location of Job to be searched") String location) {

		return ResponseEntity.ok(
				new ResponseDto(false, JobConstants.SUCCESFULLY_SEARCHED, userService.searchJobs(jobtitle, location)));

	}

	@GetMapping("/jobs")
	@Operation(summary = "Get All Jobs API")
	public ResponseEntity<ResponseDto> getAllJobs() {

		return ResponseEntity.ok(new ResponseDto(false, JobConstants.ALL_JOBS_FOUND, userService.getAllJobs()));
	}

}
