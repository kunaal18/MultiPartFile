package com.tyss.job.portal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.job.portal.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

	List<Job> findByJobTitle(String profile);

	List<Job> findByLocation(String location);

	List<Job> findByLocationOrJobTitle(String location, String profile);

	List<Job> findByJobTitleAndExperienceAndSkillsSet(String title, String exp, String skills);

}
