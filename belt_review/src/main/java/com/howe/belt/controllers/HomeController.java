package com.howe.belt.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.howe.belt.models.LoginUser;
import com.howe.belt.models.Meal;
import com.howe.belt.models.User;
import com.howe.belt.services.MealService;
import com.howe.belt.services.UserService;



@Controller
public class HomeController {
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private MealService mealServ;
	
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {
		userServ.register(newUser, result);
		if(result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/home";
	}
	
	@PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
        
		// Use login method to find 
		User user = userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
        session.setAttribute("user_id", user.getId());
        return "redirect:/home";
    }
	
	@GetMapping("/home")
	public String home(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Long loggedInUserId = (Long)session.getAttribute("user_id");
		
		if(loggedInUserId == null) {
			redirectAttributes.addFlashAttribute("notAllowed", "You must login first!");
			return "redirect:/";
		}
		
		// Use id from session to retrieve matching id from database
		User user = userServ.findUser(loggedInUserId);
		model.addAttribute("user", user);
		
		// Retrieve all meals from meals service
		List<Meal> allMeals = this.mealServ.findAllMeals();
		
		// Pass to template
		model.addAttribute("allMeals",allMeals);
		
		return "home.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user_id");
		return "redirect:/";
	}
	
	@GetMapping("/meals/new")
	public String newMeal(@ModelAttribute("meal") Meal meal) {
		return "newMeal.jsp";
	}
	
	@PostMapping("/meals/create")
	public String createMeal(@Valid @ModelAttribute("meal") Meal meal, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "newMeal.jsp";
		}
		// Get the id of the logged in user
		Long idOfUser = (Long)session.getAttribute("user_id");
		
		// Get the user using the id
		User loggedUser = this.userServ.findUser(idOfUser);
		
		// Set the meals creator to be logged in user
		meal.setUploader(loggedUser);
		
		
		this.mealServ.createMeal(meal);
		
		
		return "redirect:/home";
	}
	
	@GetMapping("/meal/info/{id}")
	public String showMealInfo(@PathVariable("id") Long id, Model model) {
		// Get meal from database
		Meal mealobj = this.mealServ.getMeal(id);
		
		// Pass meal object to template
		model.addAttribute("meal", mealobj);
		return "mealInfo.jsp";
	}
	
	@GetMapping("/meal/{id}/edit")
	public String editMeal(@PathVariable("id") Long id, Model model) {
		// Get meal with id from database
		Meal mealobj = this.mealServ.getMeal(id);
		
		// Pass meal object to template
		model.addAttribute("meal", mealobj);
		
		return "mealEdit.jsp";
	}
	
	@PostMapping("/meal/update/{id}")
	public String updateMeal(@Valid @ModelAttribute("meal") Meal meal, BindingResult result, HttpSession session, @PathVariable("id") Long id) {
		if(result.hasErrors()) {
			return "newEdit.jsp";
		}
		// Get the id of the logged in user
		Long idOfUser = (Long)session.getAttribute("user_id");
				
		// Get the user using the id
		User loggedUser = this.userServ.findUser(idOfUser);
		
		// Set the meals creator to be logged in user
		meal.setUploader(loggedUser);
		
		this.mealServ.updateMeal(meal);
		
		return "redirect:/home";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMeal(@PathVariable("id") Long id) {
		this.mealServ.deleteMeal(id);
		return "redirect:/home";
	}
	
	

}
