package com.tyss.job.portal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.job.portal.dto.JobDto;
import com.tyss.job.portal.dto.LoginDto;
import com.tyss.job.portal.dto.ResponseDto;
import com.tyss.job.portal.dto.UserDto;
import com.tyss.job.portal.service.UserService;

@SpringBootTest
class JobControllerTest {

	private MockMvc mockMvc;

	@Mock 
	private UserService service;

	@InjectMocks
	private JobController controller;
 
	ObjectMapper objectMapper = new ObjectMapper(); 

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	} 

	UserDto userDto = new UserDto(1, "sumeet", "s@gmail.com", 987456321L, "b.tech", "male", "123");
	
	LoginDto loginDto = new LoginDto("s@gmail.com", "123"); 
	JobDto jobDto = new JobDto("java Dev", "2yrs", "java", "Bangaluru", 987456321l, null);
	List<JobDto> list = List.of(jobDto);

	@Test
	void register_success() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		when(service.register(any())).thenReturn(userDto);
		
		String contentAsString = mockMvc
				.perform(post("/api/v1/register").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		ResponseDto readValue = objectMapper.readValue(contentAsString, ResponseDto.class);
		
		assertEquals("User Registration Successfull", readValue.getMsg());
	}

	@Test
	void login_success() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		when(service.login(any())).thenReturn(userDto);
		
		String contentAsString = mockMvc
				.perform(post("/api/v1/login").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseDto readValue = objectMapper.readValue(contentAsString, ResponseDto.class);
		assertEquals("Successfully LoggedIn", readValue.getMsg());
	}

	@Test
	void postJob_success() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		when(service.postJobs(any())).thenReturn(jobDto);
		String contentAsString = mockMvc
				.perform(post("/api/v1/postJob").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(jobDto)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseDto readValue = objectMapper.readValue(contentAsString, ResponseDto.class);
		assertEquals("Succesfully Posted Jobs", readValue.getMsg());
	}

	@Test
	void searchJobs_success() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		when(service.searchJobs(anyString(), anyString())).thenReturn(list);
		String contentAsString = mockMvc
				.perform(get("/api/v1/job").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(list)).param("jobtitle", "java dev")
						.param("location", "Bnagaluru"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseDto readValue = objectMapper.readValue(contentAsString, ResponseDto.class);
		assertEquals("Jobs Found Successfully", readValue.getMsg());
	}

	@Test
	void getAllJobs_success() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		when(service.getAllJobs()).thenReturn(list);
		String contentAsString = mockMvc
				.perform(get("/api/v1/jobs").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(list)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseDto readValue = objectMapper.readValue(contentAsString, ResponseDto.class);
		assertEquals("Successfully found All Jobs", readValue.getMsg());
	}
}
