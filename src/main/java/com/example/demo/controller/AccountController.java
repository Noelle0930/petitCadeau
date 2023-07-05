package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.EventRepository;
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
	
	@Autowired
	EventRepository eventRepository;
	
	@GetMapping("/")
	 public String start() {
		 return "top";
	 }

	@GetMapping({ "/login", "/logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	@PostMapping("/login")
	public String login(
			@RequestParam(name = "email1", required = false) String email1,
			@RequestParam(name = "password", required = false) String password,
			Model m) {

		Optional<User> login = userRepository.findByEmailAndPassword(email1, password);

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
			account.setBirthday(user.getBirthday());
			page = "redirect:/events";
		}

		m.addAttribute("email1", email1);

		return page;
	}

	@GetMapping("/account/add")
	public String createUser() {
		return "addUser";
	}

	@PostMapping("/account/add")
	public String storeUser(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "email2", defaultValue = "") String email2,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "birthday", required = false) LocalDate birthday,
			Model model) {

		Optional<User> record = userRepository.findByEmail(email2);

		List<String> error = new ArrayList<>();

		if (name.equals("")) {
			error.add("名前は必須です");
		}
		if (address.equals("")) {
			error.add("住所は必須です");
		}
		if (tel.equals("")) {
			error.add("電話番号は必須です");
		}
		if (email2.equals("")) {
			error.add("メールアドレスは必須です");
		}
		if (record.isEmpty() == false) {
			error.add("登録済みメールアドレスは登録できません");
		}
		if (password.equals("")) {
			error.add("パスワードは必須です");
		}
		if (birthday == null) {
			error.add("誕生日は必須です");
		}
		
		LocalDate now = LocalDate.now();
		
		if (birthday != null) {
		if (birthday.isAfter(now) || birthday.compareTo(now) == 0) {
			error.add("誕生日は当日以前を指定してください");
		}}

		model.addAttribute("name", name);
		model.addAttribute("address", address);
		model.addAttribute("tel", tel);
		model.addAttribute("email2", email2);
		model.addAttribute("password", password);
		model.addAttribute("birthday", birthday);
		model.addAttribute("List", error);

		if (error.size() == 0) {
			User user = new User(name, address, email2, tel, password, birthday);
			userRepository.save(user);

			Integer userId = user.getId();
			
			LocalDate addBirthday = LocalDate.of(2023, birthday.getMonthValue(), birthday.getDayOfMonth());

			Event event = new Event(userId, "Happy Birthday!!", addBirthday);
			eventRepository.save(event);
			
			return "redirect:/login";
		}

		return "/login";
	}
	
	@GetMapping("/mypage/{id}")
	public String mypage() {
		return "myPage";
	}
	
	@PostMapping("/mypage/{id}")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "birthday", required = false) LocalDate birthday,
			Model model) {
		
		User user = new User(id, name, address, email, tel, password, birthday);
		
		userRepository.save(user);
		
		
		return "redirect:/login";
	}
}