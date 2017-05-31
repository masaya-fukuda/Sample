package com.kintai.web.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.kintai.web.validation.GroupOrder1;
import com.kintai.web.validation.GroupOrder2;

import lombok.Data;

@Data
public class ChangeDateForm {
	@NotBlank(groups={GroupOrder1.class},message = "日付を選択してください。")
	@Pattern(groups={GroupOrder2.class},regexp = "^(\\d{4})/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$",message="入力形式が不正です。")
	private String changeDate;

	
	public String getChangeDate(){
		return changeDate;
	}
	
	public void setChangeDate(String changeDate){
		this.changeDate = changeDate;
	}

}
	
