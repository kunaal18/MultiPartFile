package com.tyss.job.portal.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class JobTest {

	ObjectMapper objectMapper = new ObjectMapper();

	String json = "{\"id\":1,\"jobTitle\":\"Sql\",\"experience\":\"2yrs\",\"skillsSet\":\"java\",\"location\":\"Benga\",\"mobileNo\":987456321,\"description\":null}";

	@Test
	void serializeTestForJob() throws JsonProcessingException {
		String writeValueAsString = objectMapper.writeValueAsString(objectMapper.readValue(json, Job.class));
		assertThat(writeValueAsString).isEqualTo(json);
	}

	@Test
	void deserializeTestForJob() throws JsonMappingException, JsonProcessingException {
		Job readValue = objectMapper.readValue(json, Job.class);
		assertEquals("Sql", readValue.getJobTitle());
	}

}
