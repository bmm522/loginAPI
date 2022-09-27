package com.SignUp.email.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SignUp.email.Service.EmailServiceImpl;
import com.SignUp.email.VO.EmailVo;

@CrossOrigin(origins="http://127.0.0.1:5501/", allowedHeaders="http://127.0.0.1:5501/")
@RestController
public class EmailController {
	
	@Autowired
	private EmailServiceImpl es;
	
	@PostMapping("/signup/email/randomnumber")
	@ResponseBody
	public Map<String, Object> sendEmail(EmailVo emailVo) {
		HashMap<String, Object> randomNumber = new HashMap<String, Object>();
		randomNumber.put("randomNumber", es.sendEamil(emailVo));
		return randomNumber;
		
		
	}
	
	
	
	
	
	
}
