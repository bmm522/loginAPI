package com.LoginAndSignUp.ChangePwd.ByEmail.EmailForm.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LoginAndSignUp.ChangePwd.ByEmail.EmailForm.DAO.ChangePwdByEmailFormDAO;
import com.LoginAndSignUp.ChangePwd.ByEmail.EmailForm.DTO.ChangePwdByEmailFormDTO;
import com.LoginAndSignUp.Repository.Member;
import com.LoginAndSignUp.SignUp.RandomNumber;
import com.LoginAndSignUp.SignUp.email.API.EmailAuthenticationCode;

@Service
public class ChangePwdByEmailFormServiceImpl  implements ChangePwdByEmailFormService{
	@Autowired
	private ChangePwdByEmailFormDAO changePwdByEmailFormDAO;
	
	@Override
	public String changePwdByEmail(ChangePwdByEmailFormDTO changePwdByEmailFormDTO) {
		 if(changePwdByEmailFormDAO.changePwdByEmail(Member.MemberBuilder()
																										  .userId(changePwdByEmailFormDTO.getUserId())
																										  .userEmail(changePwdByEmailFormDTO.getUserEmail())
																										  .build())) {
			return sendCode(changePwdByEmailFormDTO); 
		 }
		return "";
	}

	@Override
	public String sendCode(ChangePwdByEmailFormDTO changePwdByEmailFormDTO) {
		RandomNumber cr = new RandomNumber();
		String randomNumber = cr.makeRandomNumber();
		EmailAuthenticationCode.sendMail("이메일인증코드",
																			randomNumber, 
																			changePwdByEmailFormDTO.getUserEmail());
		return randomNumber;
	}

	
	
	
	
}
