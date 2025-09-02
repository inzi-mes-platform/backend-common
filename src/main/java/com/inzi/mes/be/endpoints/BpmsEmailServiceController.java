package com.inzi.mes.be.endpoints;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/test/email")
@Slf4j
public class BpmsEmailServiceController {

	@RequestMapping(value="/send", method=RequestMethod.POST)
	public void addBookmark(@RequestBody EmailSendRequest emailSendRequest) {
		log.debug("");
		log.info("Email send request: " + emailSendRequest.toString());
	}
	
	@Data
	public class EmailSendRequest {
		private String title;
		private List<String> receivers;
		private String emailBody;
		public String toString() {
			return new StringBuffer().toString();
		}
	}
}
