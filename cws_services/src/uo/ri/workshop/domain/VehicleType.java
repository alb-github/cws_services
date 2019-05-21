package uo.ri.workshop.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.Argument;

public class VehicleType {
	private Long id;

	private String name;
	private Double pricePerHour;

	private Set<Vehicle> vehicles = new HashSet<Vehicle>();

	VehicleType() {	}

	public VehicleType(String name) {
		super();
		Argument.isTrue(name != null && name.length() > 0);
		this.name = name;
	}

	public VehicleType(String name, double pricePerHour) {
		this(name);
		setPrecioHora(pricePerHour);
	}

	public Long getId() {
		return id;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPrecioHora(double pricePerHour) {
		Argument.isTrue(pricePerHour > 0);
		this.pricePerHour = pricePerHour;
	}

	public String getNombre() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoVehiculo [name=" + name 
				+ ", pricePerHour=" + pricePerHour
				+ "]";
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
