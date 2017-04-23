package com.ever.POS.best.model;

public class Client {

	private int clientId;
	private String name;
	private String address;
	private String telephone;

	public Client() {
	}

	public Client(int clientId, String name, String address, String telephone) {
		this.clientId = clientId;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getClientId() {
		return clientId;
	}



}
