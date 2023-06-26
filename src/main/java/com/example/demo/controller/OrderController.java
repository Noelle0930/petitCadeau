package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	// 注文内容確認とお客様情報入力画面を表示
	@GetMapping("/order")
	public String index(Model model) {

		model.addAttribute("name",account.getName());
		model.addAttribute("address",account.getAddress());
		model.addAttribute("email",account.getEmail());
		model.addAttribute("tel",account.getTel());
		return "order";
	}
	
	

//	// 注文内容およびお客様情報内容の確認画面を表示
//	@PostMapping("/order/confirm")
//	public String confirm(
//			@RequestParam("name") String name,
//			@RequestParam("address") String address,
//			@RequestParam("tel") String tel,
//			@RequestParam("email") String email,
//			Model model) {
//
//		// お客様情報をまとめる
//		Customer customer = new Customer(name, address, tel, email);
//		model.addAttribute("customer", customer);
//
//		return "orderConfirm";
//	}
//
//	// 注文する
//	@PostMapping("/order")
//	public String order(
//			@RequestParam("name") String name,
//			@RequestParam("address") String address,
//			@RequestParam("tel") String tel,
//			@RequestParam("email") String email,
//			Model model) {
//
//		// 1. お客様情報をDBに格納する
//		Customer customer = new Customer(name, address, tel, email);
//		//↑の時はまだidはnull、↓saveしたらidが割り振られる
//		customerRepository.save(customer);
//
//		// 2. 注文情報をDBに格納する
//		Order order = new Order(
//				customer.getId(),
//				LocalDate.now(),
//				cart.getTotalPrice());
//		orderRepository.save(order);//orderのidを取得
//
//		// 3. 注文詳細情報をDBに格納する
//		List<Item> itemList = cart.getItems();//カートの中の商品を取得
//		List<OrderDetail> orderDetails = new ArrayList<>();
//		
//		for (Item item : itemList) {
//			orderDetails.add(
//					new OrderDetail(
//							order.getId(),
//							item.getId(),//itemクラスからidを取得
//							item.getQuantity()//itemクラスから数量を取得
//					)
//			);
//		}
//		
//		orderDetailRepository.saveAll(orderDetails);//すべての商品を取得
//
//		// セッションスコープのカート情報をクリアする
//		cart.clear();
//
//		// 画面返却用注文番号を設定する
//		model.addAttribute("orderNumber", order.getId());
//
//		return "ordered";
//	}
}
