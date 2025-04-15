package com.inzi.mes.be.endpoints;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CommonWebPageRoutingController {

//	@GetMapping("/user/login")
//	public RedirectView interfs(ModelMap model) {
//		log.debug("");
//		return new RedirectView("redirect:#/user/login");
//	}
}
