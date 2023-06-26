package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	public String index() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam(name="email", required=false) String email,
			@RequestParam(name="password", required=false) String password,
			Model m) {
		
		Optional<User> login = userRepository.findByEmailAndPassword(email, password);
		
		String page;
		String message = null;
		
		if (login.isEmpty()) {
			message = "メールアドレスとパスワードが一致しませんでした";
			page = "login";
			m.addAttribute("message", message);
		} else {
			User user = login.get();
			account.setId(user.getId());
			account.setName(user.getName());
			account.setAddress(user.getAddress());
			account.setEmail(user.getEmail());
			account.setTel(user.getTel());	
			page = "redirect:/events";
		}
		
		return page;
	}
	
	@GetMapping("/account/add")
	public String createUser() {
		return "addUser";
	}
	
	@PostMapping("/account/add")
	public String storeUser(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "birthday") LocalDate birthday,
			Model model) {
		
		User user = new User(name, address, email, tel, password, birthday);
		userRepository.save(user);
		
		return "redirect:/login";
	}
}








