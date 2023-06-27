package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.model.Account;
import com.example.demo.model.Event;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	Account account;
	
	@Autowired
	Event event;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;
	
	@GetMapping("/items/{id}")
	public String index2(
			@PathVariable("id") Integer id,
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice,
			Model model) {

		// 全カテゴリー一覧を取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 商品一覧情報の取得
		List<Item> itemList = null;

		if (categoryId != null && maxPrice != null) {//カテゴリあり、価格あり
			itemList = itemRepository.findByPriceLessThanEqualAndCategoryId(maxPrice, categoryId);

		} else if (categoryId != null && maxPrice == null) {//カテゴリあり、価格なし
			itemList = itemRepository.findByCategoryId(categoryId);

		} else if (categoryId == null && maxPrice != null) {//カテゴリなし、価格あり
			itemList = itemRepository.findByPriceLessThanEqual(maxPrice);

		} else if (categoryId == null && maxPrice == null) {//カテゴリなし、価格なし
			itemList = itemRepository.findAll();

		}
		
		event.setId(id);

		model.addAttribute("items", itemList);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("categoryId", categoryId == null ? "" : categoryId);
		model.addAttribute("id", id);

		/*
		 * 三項演算子
		 * 変数 = 条件 ? 値１ : 値２
		 */

		return "items";
	}

}