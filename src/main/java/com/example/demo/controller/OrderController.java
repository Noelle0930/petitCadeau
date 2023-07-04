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
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.model.Account;
import com.example.demo.model.Cart;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

	@Autowired
	Cart cart;

	@Autowired
	Account account;
	
	@Autowired
	Event event;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	// 注文内容確認とお客様情報入力画面を表示
	@GetMapping("/order")
	public String index(Model model) {

		model.addAttribute("name", account.getName());
		model.addAttribute("address", account.getAddress());
		model.addAttribute("email", account.getEmail());
		model.addAttribute("tel", account.getTel());
		return "order";
	}

	// 注文内容およびお客様情報内容の確認画面を表示
	@PostMapping("/order/confirmme")
	public String confirmme(
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam(name = "payment1", required = false) String payment1,
			@RequestParam(name = "payment2", required = false) String payment2,
			@RequestParam("message") String message,
			@RequestParam("sendTo") String sendTo,
			Model model) {
		
		List<String> error1 = new ArrayList<>();
		
		model.addAttribute("name", name);
		model.addAttribute("address", address);
		model.addAttribute("payment1", payment1);
		model.addAttribute("payment2", payment2);
		model.addAttribute("message", message);
		model.addAttribute("sendTo", sendTo);
		
		if (name.equals("")) {
			error1.add("名前は必須です");
		}
		if (address.equals("")) {
			error1.add("住所は必須です");
		}
		if(!(message.equals(""))) {
			if(message.length()>30) {
			error1.add("30字以内で入力してください");	
			}
		}
		
		if (error1.size() != 0) {
			model.addAttribute("error1", error1);
			return "order";
		}

		
		return "orderConfirm";
	}

	
	// 注文内容およびお客様情報内容の確認画面を表示
		@PostMapping("/order/confirmyou")
		public String confirmyou(
				@RequestParam(name="toname",defaultValue="") String toname,
				@RequestParam(name="toaddress",defaultValue="") String toaddress,
				@RequestParam(name = "payment1", required = false) String payment1,
				@RequestParam(name = "payment2", required = false) String payment2,
				@RequestParam("tomessage") String tomessage,
				@RequestParam("sendTo") String sendTo,
				Model model) {
			
			List<String> error2 = new ArrayList<>();


			model.addAttribute("toname", toname);
			model.addAttribute("toaddress", toaddress);
			model.addAttribute("payment1", payment1);
			model.addAttribute("payment2", payment2);
			model.addAttribute("tomessage", tomessage);
			model.addAttribute("sendTo", sendTo);
			
			if (toname.equals("")) {
				error2.add("宛名は必須です");
			}
			if (toaddress.equals("")) {
				error2.add("送り先住所は必須です");
			}
			if(!(tomessage.equals(""))) {
				if(tomessage.length()>30) {
					error2.add("30字以内で入力してください");	
				}
			}
			
			
			if (error2.size() != 0) {
				model.addAttribute("error2", error2);
				return "order";
			}
			
			return "orderConfirm";
		}
	
	// 注文を確定する
	@PostMapping("/order")
	public String order(
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "payment1", required = false) String payment1,
			@RequestParam(name = "payment2", required = false) String payment2,
			@RequestParam("message") String message,
			Model model) {

		String payment = null;

		if (payment1 != null) {
			payment = payment1;
		} else if (payment2 != null) {
			payment = payment2;
		}
		
		Order order;

		if (event.getName().equals("Happy Birthday!!")) {
			order = new Order(
						event.getId(),
						LocalDate.now(),
						address,
						cart.getTotalPrice2(),
						message,
						payment);
				orderRepository.save(order);
		}
		
		// 2. 注文情報をDBに格納する
		else{order = new Order(
				event.getId(),
				LocalDate.now(),
				address,
				cart.getTotalPrice(),
				message,
				payment);
		orderRepository.save(order);//orderのidを取得
	    }
		
		// 3. 注文詳細情報をDBに格納する
		List<Item> itemList = cart.getItems();//カートの中の商品を取得
		List<OrderDetail> orderDetails = new ArrayList<>();

		for (Item item : itemList) {
			orderDetails.add(
					new OrderDetail(
							order.getId(),
							item.getId(), //itemクラスからidを取得
							item.getQuantity()//itemクラスから数量を取得
					));
		}

		orderDetailRepository.saveAll(orderDetails);//すべての商品を取得

		// セッションスコープのカート情報をクリアする
		cart.clear();

		return "ordered";
	}
}
