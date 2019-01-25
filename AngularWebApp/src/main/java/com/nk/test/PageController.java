package com.nk.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

	@RequestMapping("/")
	public String hello(){
		return "hello";
	}
	
}
