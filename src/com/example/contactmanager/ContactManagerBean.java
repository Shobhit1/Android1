/**********************************************************
 * 
 * Class : AddContact.java
 * Description : This class contains act as an intermediate class 
 * 				for data to be transmitted to different classes.
 * 				It acts as a Bean in OOPS terminology.Contains setter and getters.
 * 
 *********************************************************/
package com.example.contactmanager;

import java.io.Serializable;

public class ContactManagerBean implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	
	public ContactManagerBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ContactManagerBean(String fname, String lastName,String phoneNumber, String email)
	{
		super();
		this.firstName = fname;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
