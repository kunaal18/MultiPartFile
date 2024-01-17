package com.tyss.job.portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyss.job.portal.constants.ExceptionConstants;
import com.tyss.job.portal.dao.JobRepository;
import com.tyss.job.portal.dao.UserRepository;
import com.tyss.job.portal.dto.JobDto;
import com.tyss.job.portal.dto.LoginDto;
import com.tyss.job.portal.dto.UserDto;
import com.tyss.job.portal.entity.Job;
import com.tyss.job.portal.entity.User;
import com.tyss.job.portal.exception.JobsNotFoundException;
import com.tyss.job.portal.exception.SomeThingWentWrongException;
import com.tyss.job.portal.exception.UserNotFoundException;
import com.tyss.job.portal.exception.UserPresentException;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto login(LoginDto loginDto) {
		Optional<User> findByUserName = userRepository.findByEmailAndPassword(loginDto.getEmail(),
				loginDto.getPassword());

		if (findByUserName.isPresent()) {
			User user = findByUserName.get();
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		} else {
			throw new UserNotFoundException(ExceptionConstants.EXCEPTION_USER_NOT_FOUND);
		}
	}

	@Override
	public UserDto register(UserDto userDto) {
		Optional<User> findByUserName = userRepository.findByEmail(userDto.getEmail());

		if (!findByUserName.isPresent()) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			User save = userRepository.save(user);
			BeanUtils.copyProperties(save, userDto);
			return userDto;
		}
		throw new UserPresentException(ExceptionConstants.EXCEPTION_USER_FOUND);
//		return userRepository.findByUserName(userDto.getUserName()).filter(Objects::isNull).map(x -> {
//			System.err.println("inside map");
//			User user = new User();
//			BeanUtils.copyProperties(userDto, user);
//			User save = userRepository.save(user);
//			UserDto dto = new UserDto();
//			BeanUtils.copyProperties(save, dto);
//			return dto;
//		}).orElseThrow(() -> new UserPresentException("UserAccount present, please login"));

	}

	@Override
	public JobDto postJobs(JobDto postDto) {
//		List<Job> jobList = jobRepository.findByJobTitle(postDto.getJobTitle());
//		List<JobDto> dtos = new ArrayList<>();
//		jobList.forEach(job -> {
//			JobDto dto = new JobDto();
//			BeanUtils.copyProperties(job, dto);
//			dtos.add(dto);
//		});
//		List<JobDto> dtoList = new ArrayList<>();
//		dtoList.add(postDto);
//		if (!dtoList.containsAll(dtos)) {

		List<Job> jobList = jobRepository.findByJobTitleAndExperienceAndSkillsSet(postDto.getJobTitle(),
				postDto.getExperience(), postDto.getSkillsSet());
		if (jobList.isEmpty()) {
			Job job = new Job();
			BeanUtils.copyProperties(postDto, job);
			Job save = jobRepository.save(job);
			BeanUtils.copyProperties(save, postDto);
			return postDto;
		}

		throw new SomeThingWentWrongException(ExceptionConstants.EXCEPTION_SOMETHING_WRONG);

	}

	@Override
	public List<JobDto> searchJobs(String jobtitle, String location) {

		List<Job> findByLocationOrJobTitle = jobRepository.findByLocationOrJobTitle(location, jobtitle);

		List<JobDto> dtoList = new ArrayList<>();
		if (!findByLocationOrJobTitle.isEmpty()) {
			findByLocationOrJobTitle.forEach(job -> {
				JobDto jobDto = new JobDto();
				BeanUtils.copyProperties(job, jobDto);
				dtoList.add(jobDto);
			});

//			for (Job job : findByProfile) {
//				JobDto jobDto = new JobDto();
//				BeanUtils.copyProperties(job, jobDto);
//				dtoList.add(jobDto);
//			}

			return dtoList;
		}
		throw new JobsNotFoundException(ExceptionConstants.EXCEPTION_JOB_NOT_FOUND);
	}

	@Override
	public List<JobDto> getAllJobs() {
		List<Job> findAll = jobRepository.findAll();
		List<JobDto> dtosList = new ArrayList<>();
		if (!findAll.isEmpty()) {
//			for (Job job : findAll) {
//				JobDto dto = new JobDto();
//				BeanUtils.copyProperties(job, dto);
//				dtosList.add(dto);
//			}

			findAll.forEach(job -> {
				JobDto dto = new JobDto();
				BeanUtils.copyProperties(job, dto);
				dtosList.add(dto);
			});
			return dtosList;
		}
		throw new JobsNotFoundException(ExceptionConstants.EXCEPTION_JOB_NOT_FOUND);
	}

}
