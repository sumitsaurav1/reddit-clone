package com.javateam.repository;
import com.javateam.model.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface MediaRepository extends CrudRepository<Media,Integer>{
}
