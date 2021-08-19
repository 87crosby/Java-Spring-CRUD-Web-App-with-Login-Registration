package com.howe.belt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howe.belt.models.Meal;
import com.howe.belt.repositories.MealRepository;

@Service
public class MealService {
	
	@Autowired
    private MealRepository mealRepo;
	
	public List<Meal> findAllMeals(){
		return (List<Meal>) this.mealRepo.findAll();
	}
	
	public Meal createMeal(Meal meal) {
		return this.mealRepo.save(meal);
	}
	
	public Meal getMeal(Long id) {
		return this.mealRepo.findById(id).orElse(null);
	}
	
	public Meal updateMeal(Meal meal) {
		return this.mealRepo.save(meal);
	}
	
	public void deleteMeal(Long id) {
		this.mealRepo.deleteById(id);
	}

}
