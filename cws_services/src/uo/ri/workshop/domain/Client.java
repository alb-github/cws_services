package uo.ri.workshop.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.Argument;

public class Client {
	private Long id;
	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;
	
	private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
	
	public Client(String dni) {
		super();
		Argument.isTrue( dni != null && dni.length() > 0 );
		this.dni = dni;
	}

	Client() { }
	
	public Client(String dni, String name, String surname) {
		this( dni );
		this.name = name;
		this.surname = surname;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String apellidos) {
		this.surname = apellidos;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni 
				+ ", name=" + name 
				+ ", apellidos=" + surname 
				+ ", email=" + email
				+ ", phone=" + phone 
				+ ", address=" + address 
				+ "]";
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}
	
	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>( paymentMeans );
	}
	

	public Long getId() {
		return id;
	}
}

