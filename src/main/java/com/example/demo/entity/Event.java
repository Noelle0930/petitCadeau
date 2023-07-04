package com.example.demo.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Data
@Entity
@SessionScope
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	private String name;

	@Column(name = "event_date")
	private LocalDate eventDate;

	public Event() {

	}

	//更新用コンストラクタ
	public Event(Integer id, Integer userId, String name, LocalDate eventDate) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.eventDate = eventDate;
	}

	public Event(Integer userId, String name, LocalDate eventDate) {
		this.userId = userId;
		this.name = name;
		this.eventDate = eventDate;
	}

	//	public Event(String name,LocalDate eventDate) {
	//		this.name = name;
	//		this.eventDate = eventDate;
	//	}
}
