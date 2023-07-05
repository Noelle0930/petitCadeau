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

		List<Event> eventList = eventRepository.findByUserIdOrderByEventDate(userId);

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
		if (name.equals("Happy Birthday!!")) {
			error.add("「Happy Birthday!!」は使用できません");
		}

		LocalDate now = LocalDate.now();
		LocalDate yesterday= now.minusDays(1);

		if (eventDate != null) {
			if (eventDate.isBefore(yesterday)) {
				error.add("イベント日は当日以降を指定してください");
			}
		}

		model.addAttribute("name", name);
		model.addAttribute("eventDate", eventDate);
		model.addAttribute("List", error);

		if (error.size() == 0) {
			Integer userId = account.getId();

			Event event = new Event(userId, name, eventDate);
			eventRepository.save(event);

			return "redirect:/events";
		}

		return "/addEvent";
	}

	@GetMapping("/events/{id}/edit")
	public String edit(
			@PathVariable(name = "id", required = false) Integer id,
			Model model) {

		Event event = null;

		Optional<Event> record = eventRepository.findById(id);

		if (record.isEmpty() == false) {
			event = record.get();
		}

		if (event == null) {
			return "redirect:/events";
		}
		

		model.addAttribute("event", event);

		return "editEvent";
	}

	@PostMapping("/events/{id}/edit")
	public String update(
			@PathVariable(name = "id", required = false) Integer id,
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
		if (name.equals("Happy Birthday!!")) {
			error.add("「Happy Birthday!!」は使用できません");
		}

		LocalDate now = LocalDate.now();

		if (eventDate != null) {
			if (eventDate.isBefore(now) || eventDate.compareTo(now) == 0) {
				error.add("イベント日は翌日以降を指定してください");
			}
		}

		model.addAttribute("name", name);
		model.addAttribute("eventDate", eventDate);
		model.addAttribute("List", error);
		
		if (error.size() == 0) {
		Event event = new Event(id, account.getId(), name, eventDate);

		eventRepository.save(event);

		return "redirect:/events";
		}
		
		Event event = eventRepository.findById(id).get();
		model.addAttribute("event", event);
		
		return "editEvent";
	}

	@PostMapping("/events/{id}/delete")
	public String delete(
			@PathVariable(name = "id", required = false) Integer id) {
		eventRepository.deleteById(id);

		return "redirect:/events";
	}
}
