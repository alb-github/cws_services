package uo.ri.workshop.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.Argument;

public class SparePart {
	private Long id;

	private String code;
	private String description;
	private double price;

	protected Set<Substitution> substitutions = new HashSet<Substitution>();

	SparePart() {}

	public SparePart(String code) {
		super();
		Argument.isTrue(code != null && code.length() > 0);
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public SparePart(String code, String description, double price) {
		this(code);
		setDescription(description);
		setPrice(price);
	}

	public String getDescripcion() {
		return description;
	}

	public void setDescription(String description) {
		Argument.isTrue(description != null && description.length() > 0);
		this.description = description;
	}

	public double getPrecio() {
		return price;
	}

	public void setPrice(double price) {
		Argument.isTrue(price >= 0);
		this.price = price;
	}

	public String getCodigo() {
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		SparePart other = (SparePart) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code 
				+ ", description=" + description
				+ ", price=" + price + "]";
	}

	public Set<Substitution> getSustituciones() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSustituciones() {
		return substitutions;
	}

}
