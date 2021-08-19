package com.howe.belt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.howe.belt.models.Meal;

@Repository
public interface MealRepository extends CrudRepository<Meal,Long> {

}
