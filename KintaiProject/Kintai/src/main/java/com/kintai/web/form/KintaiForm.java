package com.kintai.web.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.kintai.web.validation.GroupOrder1;
import com.kintai.web.validation.GroupOrder2;
import com.kintai.web.validation.constraints.UserNumber;

import lombok.Data;

@Data
public class KintaiForm implements Serializable{
	@NotBlank(groups={GroupOrder1.class},message = "社員番号を入力してください。")
	@Pattern(groups={GroupOrder2.class},regexp = "^[0-9a-zA-Z_]+$",message="社員番号は半角英数字で入力してください。")
	@Size(max=15,groups={GroupOrder2.class},message="社員番号は15文字以内で入力してください。")	
	@UserNumber(groups={GroupOrder2.class})
	private String userNumber;
	
	@NotBlank(groups={GroupOrder1.class},message = "日付を選択してください。")
	@Pattern(groups={GroupOrder2.class},regexp = "^(\\d{4})/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$",message="入力形式が不正です。")
	private String date;
	
	@NotBlank(groups={GroupOrder1.class},message = "事由を選択してください。")
	private String type;
	
	@NotBlank(groups={GroupOrder1.class},message = "時間帯を選択してください。")
	private String ampm;
	
	@Size(max=15,groups={GroupOrder2.class},message="時間は半角15文字以内で入力してください。")
	private String time;
	
	@Size(max=30,groups={GroupOrder2.class},message="備考は30文字以内で入力してください。")
	private String description;
	
	
	public String getUserNumber(){
		return userNumber;
	}
	
	public void setUserNumber(String userNumber){
		this.userNumber = userNumber;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getAmpm(){
		return ampm;
	}
	
	public void setAmpm(String ampm){
		this.ampm = ampm;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
}
