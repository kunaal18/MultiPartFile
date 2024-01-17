package com.tyss.job.portal.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

@SpringBootTest
class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl service;

	@Mock 
	private UserRepository repository; 

	@Mock 
	private JobRepository jobRepository;

	LoginDto loginDto = new LoginDto("s@gmail.com", "123");
	
	User user = new User(1, "sumeet", "s@gmail.com", 987456123l, "b.tech", "male", "123");
	
	UserDto userDto = new UserDto(1, "sumeet", "s@gmail.com", 987456321L, "b.tech", "male", "123");
	Job job = new Job(1, "java dev", "2yrs", "java", null, 0, null);
	List<Job> list = List.of(job);
	List<Job> emptyList = new ArrayList<>();
	JobDto jobDto = new JobDto("java dev", "2yrs", "java", "Bangaluru", 987456321l, null);

	@Test
	void login_success() {
		Optional<User> optional = Optional.of(new User(1, "sumeet", "s@gmail.com", null, null, null, "123"));
		
		when(repository.findByEmailAndPassword(anyString(), anyString())).thenReturn(optional);
		
		UserDto login = service.login(loginDto);
		
		assertEquals("s@gmail.com", login.getEmail());

	}

	@Test
	void login_fail() {
		Optional<User> optional = Optional.empty();
		
		when(repository.findByEmailAndPassword(anyString(), anyString())).thenReturn(optional);
		
		assertThatThrownBy(() -> service.login(loginDto)).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void register_success() {
		Optional<User> optional = Optional.empty();
		
		when(repository.findByEmail(anyString())).thenReturn(optional); 
		
		when(repository.save(any())).thenReturn(user);
		
		UserDto register = service.register(userDto);
		
		assertEquals("s@gmail.com", register.getEmail());
	}

	@Test
	void register_fail() {
		Optional<User> optional = Optional.of(new User(1, "sumeet", "s@gmail.com", null, null, null, "123"));
		
		when(repository.findByEmail(anyString())).thenReturn(optional);
		assertThatThrownBy(() -> service.register(userDto)).isInstanceOf(UserPresentException.class);
	}

	@Test
	void postJobs_success() {
		when(jobRepository.findByJobTitleAndExperienceAndSkillsSet(anyString(), anyString(), anyString()))
				.thenReturn(emptyList);
		when(jobRepository.save(any())).thenReturn(job);
		JobDto postJobs = service.postJobs(jobDto);
		assertEquals("java dev", postJobs.getJobTitle());
	}

	@Test
	void postJobs_fail() {
		when(jobRepository.findByJobTitleAndExperienceAndSkillsSet(anyString(), anyString(), anyString()))
				.thenReturn(list);
		assertThatThrownBy(() -> service.postJobs(jobDto)).isInstanceOf(SomeThingWentWrongException.class);
	}

	@Test
	void searchJobs_success() {
		when(jobRepository.findByLocationOrJobTitle(anyString(), anyString())).thenReturn(list);
		List<JobDto> searchJobs = service.searchJobs("Bangaluru", "java dev");
		assertEquals("java dev", searchJobs.get(0).getJobTitle());
	}

	@Test
	void searchJobs_fail() {
		when(jobRepository.findByLocationOrJobTitle(anyString(), anyString())).thenReturn(emptyList);
		assertThatThrownBy(() -> service.searchJobs("Bangaluru", "java dev")).isInstanceOf(JobsNotFoundException.class);
	}

	@Test
	void getAllJobs_success() {
		when(jobRepository.findAll()).thenReturn(list);
		List<JobDto> allJobs = service.getAllJobs();
		assertEquals("java dev", allJobs.get(0).getJobTitle());
	}

	@Test
	void getAllJobs_fail() {
		when(jobRepository.findAll()).thenReturn(emptyList);
		assertThatThrownBy(() -> service.getAllJobs()).isInstanceOf(JobsNotFoundException.class);
	}
}
