package com.te.storefile.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.te.storefile.entity.ImageData;

public interface Storage extends CrudRepository<ImageData, Integer> {

	Optional<ImageData>  findByName(String fileName);
}
