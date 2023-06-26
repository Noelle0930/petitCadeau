package com.example.demo.controller;

import java.time.LocalDate;
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
		
//		DayOfWeek day = eventList.getEventdate().getDayOfWeek();
//
//		String dayJpn = "";
//		switch (day) {
//		case MONDAY:
//			dayJpn = "(月)";
//			break;
//		case TUESDAY:
//			dayJpn = "(火)";
//			break;
//		case WEDNESDAY:
//			dayJpn = "(水)";
//			break;
//		case THURSDAY:
//			dayJpn = "(木)";
//			break;
//		case FRIDAY:
//			dayJpn = "(金)";
//			break;
//		case SATURDAY:
//			dayJpn = "(土)";
//			break;
//		case SUNDAY:
//			dayJpn = "(日)";
//			break;
//		}
		model.addAttribute("list", eventList);
		
//		model.addAttribute("day", dayJpn);

		return "events";
	}

	@GetMapping("/events/add")
	public String eventCreate() {
		return "addEvent";
	}

	@PostMapping("/events/add")
	public String eventStore(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "eventDate") LocalDate eventDate,
			Model model) {

		Integer userId = account.getId();
		
		Event event = new Event(userId, name, eventDate);
		eventRepository.save(event);
		
		return "redirect:/events";
	}
}
