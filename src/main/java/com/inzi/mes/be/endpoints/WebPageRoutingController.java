package com.inzi.mes.be.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@Slf4j
public class WebPageRoutingController {

	@GetMapping("/auth/login-redirect")
	public RedirectView sendRedirectLogin(ModelMap model) {
		log.debug("");
		return new RedirectView("redirect:#/user/#/login");
	}
}
