package com.te.storefile.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.storefile.entity.FileSystemData;

public interface FileData extends JpaRepository<FileSystemData, Integer> {
	Optional<FileSystemData> findByName(String name);
}
