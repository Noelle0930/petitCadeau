package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class Account {
	private Integer id;
	private String name;
	private String address;
	private String email;
	private String tel;
	private LocalDate birthday;
}

