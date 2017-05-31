package com.kintai.business.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
    @Id
	@Column(nullable = false, unique = true)
	private String userId;

	@Column(nullable = false)	
	private String name;

	@Column(nullable = false)
	private String password;
	
	private String phoneNumber;
	
	private String department;

	
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
	
	@OneToMany(mappedBy = "user")
	private List<Kintai> kintais;
	
	public List<Kintai> getKintais() {
	    return kintais;
	}

	public void setKintais(List<Kintai> kintais) {
	    this.kintais = kintais;
	}

}
