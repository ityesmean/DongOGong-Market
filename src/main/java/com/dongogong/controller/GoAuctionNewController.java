package com.dongogong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auction_new.do")
public class GoAuctionNewController {
	@GetMapping
	public String auctionNew() {
		return "auction_new";
	}
}
