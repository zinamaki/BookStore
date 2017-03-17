package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
	private String name;
	private String email;
	private String password;
	private Map<Date, Purchase> purchases;
	
	public User(String name, String email, String password){
		this.name = name;
		this.email = email;
		this.password = password;
		this.purchases = new HashMap<Date, Purchase>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<Date, Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Map<Date, Purchase> purchases) {
		this.purchases = purchases;
	}
	
}
