package com.kintai.business.domain;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "kintai")
public class Kintai implements Comparator<Kintai>{
	@Id
	@GeneratedValue
	private Long id;
	
	private String userNumber;
	
	private String date;
	
	private String type;
	
	private String ampm;
	
	private String time;
	
	private String description;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

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

	@Override
	public int compare(Kintai kintai1, Kintai kintai2) {
		return kintai1.getType().compareTo((kintai2.getType()));
	}
	
	@ManyToOne
	private User user;
	
	public User getUser() {
	    return user;
	}

	public void setUser(User user) {
	    this.user = user;
	}

}
