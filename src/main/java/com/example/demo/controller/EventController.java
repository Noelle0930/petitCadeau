package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/events/add")
	public String eventCreate() {
		return "addEvent";
	}

	@PostMapping("/events/add")
	public String eventStore(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "eventDate", required = false) LocalDate eventDate,
			Model model) {

		List<String> error = new ArrayList<>();

		if (name.equals("")) {
			error.add("イベント名を入力してください");
		}
		if (eventDate == null) {
			error.add("日付を指定してください");
		}

	LocalDate now = LocalDate.now();
		
		if(eventDate !=null) {
			if (eventDate.isBefore(now)||eventDate.compareTo(now)==0) {
				error.add("イベント日は翌日以降を指定してください");	
			}
		}
		
		model.addAttribute("List", error);

		if (error.size() == 0) {
			Integer userId = account.getId();

			Event event = new Event(userId, name, eventDate);
			eventRepository.save(event);

			return "redirect:/events";
		}

		return "/addEvent";
	}
}
