package uo.ri.workshop.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.Argument;

public class Mechanic {
	private Long id;
	private String dni;
	private String surname;
	private String name;

	protected Set<WorkOrder> assigned = new HashSet<>();
	protected Set<Intervention> interventions = new HashSet<>();

	Mechanic() {}

	public Mechanic(String dni) {
		super();
		Argument.isTrue(dni != null && dni.length() > 0);
		this.dni = dni;
	}

	public Mechanic(String dni, String name, String surname) {
		this(dni);
		this.name = name;
		this.surname = surname;
	}

	public Long getId() {
		return id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		Mechanic other = (Mechanic) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mecanic [dni=" + dni 
				+ ", surname=" + surname 
				+ ", name=" + name
				+ "]";
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(assigned);
	}

}
