package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String address;

	private String email;

	private String tel;

	private String password;

	private LocalDate birthday;

	public User() {

	}
	
	public User(Integer id, String name, String address, String email, String tel, String password, LocalDate birthday) {
		this.id = id; 
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.password = password;
		this.birthday = birthday;
	}

	public User(String name, String address, String email, String tel, String password, LocalDate birthday) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.password = password;
		this.birthday = birthday;
	}

}
