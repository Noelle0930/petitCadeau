package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Event;
import com.example.demo.model.Account;
import com.example.demo.repository.EventRepository;

@Controller
public class EventController {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	Account account;

	
	@GetMapping("/events")
	public String index(Model model) {
		
		Integer userId = account.getId();
		
		List<Event> eventList = eventRepository.findByUserId(userId);
		model.addAttribute("list", eventList);
		
		return "events";
	}
}
