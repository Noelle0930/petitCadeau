package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Account;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {

	@Autowired
	Cart cart;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	Account account;

	@GetMapping("/cart")
	public String index(Model model) {
		LocalDate toBirthday = LocalDate.of(2023, account.getBirthday().getMonthValue(),
				account.getBirthday().getDayOfMonth());

		model.addAttribute("birthday", toBirthday);
		model.addAttribute("now", LocalDate.now());
		return "cart";
	}

	// 指定した商品をカートに追加する
	// 数量が未指定の場合は1とする
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam("itemId") int itemId,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
			Model model) {

		LocalDate toBirthday = LocalDate.of(2023, account.getBirthday().getMonthValue(),
				account.getBirthday().getDayOfMonth());

		model.addAttribute("birthday", toBirthday);
		model.addAttribute("now", LocalDate.now());

		// 商品コードをキーに商品情報を取得する
		Item item = itemRepository.findById(itemId).get();

		// 商品オブジェクトに個数をセット
		item.setQuantity(quantity);

		// カートに追加//Cartクラスのaddメソッド
		cart.add(item);

		// 「/cart」にリダイレクト
		return "redirect:/cart";
	}

	// 指定した商品をカートから削除
	@PostMapping("/cart/delete")
	public String deleteCart(@RequestParam("itemId") int itemId) {

		// カート情報から削除
		cart.delete(itemId);

		// 「/cart」にリダイレクト
		return "redirect:/cart";
	}
}
