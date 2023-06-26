package com.example.demo.controller;

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
			account.setName(user.getName());	
			page = "redirect:/events";
		}
		
		return page;
	}
}
