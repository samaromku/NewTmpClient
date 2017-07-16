package com.example.andrey.newtmpclient.entities;

import java.util.HashSet;
import java.util.Set;

public class ContactOnAddress {
	private int id;
	private String name;
	private String post;
	private String phone;
	private String email;
	private String apartments;
	private int addressId;
	private String address;
	private String orgName;
	private Set<String>phones = new HashSet<>();
	private Set<String>emails = new HashSet<>();
	
	public void addPhone(String phone){
		phones.add(phone);
	}
	
	public Set<String>getPhones(){
		return phones;
	}
	
	public void addEmail(String email){
		emails.add(email);
	}
	
	public Set<String>getEmails(){
		return emails;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getApartments() {
		return apartments;
	}
	public void setApartments(String apartments) {
		this.apartments = apartments;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public ContactOnAddress(String orgName, String address,  String post, String name, String phone, String email, String apartments) {
		this.name = name;
		this.post = post;
		this.phone = phone;
		this.email = email;
		this.apartments = apartments;
		this.address = address;
		this.orgName = orgName;
	}
	public ContactOnAddress(int id, String name, String post, String phone, String email, String apartments, int addressId) {
		this.id = id;
		this.name = name;
		this.post = post;
		this.phone = phone;
		this.email = email;
		this.apartments = apartments;
		this.addressId = addressId;
	}

	@Override
	public String toString() {
		return "ContactOnAddress [id=" + id + ", name=" + name + ", post=" + post + ", phone=" + phone + ", email="
				+ email + ", apartments=" + apartments + ", addressId=" + addressId + ", address=" + address
				+ ", orgName=" + orgName + ", phones_size=" + phones.size() + ", emails_size=" + emails.size() + "]";
	}
}
