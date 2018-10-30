package com.fakenews.repository;

import com.fakenews.entities.ExternalSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalSourceRepository extends CrudRepository<ExternalSource, String> {

}
