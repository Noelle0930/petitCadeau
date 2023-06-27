package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "event_id")
	private Integer eventId;
	
	@Column(name = "ordered_on")
	private LocalDate orderedOn;
	
	@Column(name = "send_address")
	private String sendAddress;
	
	@Column(name = "total_price")
	private Integer totalPrice;
	
	private String message;
	
	private String payment;
	
	public Order() {
		
	}
	
	public Order(Integer eventId, LocalDate orderedOn, String sendAddress, Integer totalPrice, String message, String payment) {
		this.eventId = eventId;
		this.orderedOn = orderedOn;
		this.sendAddress = sendAddress;
		this.totalPrice = totalPrice;
		this.message = message;
		this.payment = payment;
	}
}
