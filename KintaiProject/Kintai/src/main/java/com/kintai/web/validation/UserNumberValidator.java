package com.kintai.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.kintai.business.domain.User;
import com.kintai.business.repository.UserRepository;
import com.kintai.web.validation.constraints.UserNumber;

public class UserNumberValidator implements ConstraintValidator<UserNumber, String> {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(UserNumber annotation) {
		
	}
	
    @Override
    public boolean isValid(String userNumber,
            ConstraintValidatorContext paramConstraintValidatorContext) {
    	User user = userRepository.findByUserId(userNumber);
    	if (user == null){
    		return false;
    	} else {
    		return true;
    	}
    } 
}