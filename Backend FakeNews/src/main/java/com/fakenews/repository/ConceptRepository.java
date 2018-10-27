package com.fakenews.repository;

import com.fakenews.entities.Concept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptRepository extends CrudRepository<Concept, String> {

}
