package com.kintai.web.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.kintai.web.validation.GroupOrder1;
import com.kintai.web.validation.GroupOrder2;

public class UserForm implements Serializable{
	@NotBlank(groups={GroupOrder1.class},message = "社員番号を入力してください。")
	@Pattern(groups={GroupOrder2.class},regexp = "^[0-9a-zA-Z_]+$",message="社員番号は半角英数字で入力してください。")
	@Size(max=15,groups={GroupOrder2.class},message="社員番号は15文字以内で入力してください。")
	private String userId;

	@NotBlank(groups={GroupOrder1.class},message = "氏名を入力してください。")
	@Size(max=15,groups={GroupOrder2.class},message="氏名は15文字以内で入力してください。")
	@Pattern(groups={GroupOrder2.class},regexp = "^[^ -~｡-ﾟ]*$",message="氏名は全角で入力してください。")
	private String name;
	
	@Size(max=30,groups={GroupOrder2.class},message="電話番号は30文字以内で入力してください。")
	private String phoneNumber;
	
	@Size(max=30,groups={GroupOrder2.class},message="所属は30文字以内で入力してください。")
	private String department;

	@NotBlank(groups={GroupOrder1.class},message = "パスワードを入力してください。")
	@Pattern(groups={GroupOrder2.class},regexp = "^[0-9a-zA-Z_]+$",message="パスワードは半角英数字で入力してください。")
	@Size(min=8,max=15,groups={GroupOrder2.class},message="パスワードは8文字以上15文字以内で入力してください。")
	private String password;
	
	@NotBlank(groups={GroupOrder1.class},message = "確認用パスワードを入力してください。")	
	private String confirmPassword;

	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	public String getConfirmPassword(){
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public String getDepartment(){
		return department;
	}
	
	public void setDepartment(String department){
		this.department = department;
	}
	
}

