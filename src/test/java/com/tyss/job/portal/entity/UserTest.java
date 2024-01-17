package com.tyss.job.portal.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class UserTest {

	ObjectMapper objectMapper = new ObjectMapper();

	String json = "{\"id\":1,\"userName\":\"sumeet\",\"email\":\"s@gmail.com\",\"phoneNumber\":987456321,\"education\":\"b.tech\",\"gender\":\"male\",\"password\":\"123\"}";

	@Test
	void serializeTestForUser() throws JsonProcessingException {
		String writeValueAsString = objectMapper.writeValueAsString(objectMapper.readValue(json, User.class));
		assertThat(writeValueAsString).isEqualTo(json);
	}

	@Test
	void deserializeTestForUser() throws JsonMappingException, JsonProcessingException {
		User readValue = objectMapper.readValue(json, User.class);
		assertEquals("sumeet", readValue.getUserName());
	}
}
