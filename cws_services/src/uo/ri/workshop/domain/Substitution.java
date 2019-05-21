package uo.ri.workshop.domain;

import alb.util.assertion.Argument;

public class Substitution {
	private Long id;
	private int quantity;
	private SparePart sparePart;
	private Intervention intervention;

	Substitution() {
	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		super();
		Argument.isTrue(quantity > 0);
		Associations.Sustitute.link(sparePart, this, intervention);
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((intervention == null) ? 0 : intervention.hashCode());
		result = prime * result
				+ ((sparePart == null) ? 0 : sparePart.hashCode());
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
		Substitution other = (Substitution) obj;
		if (intervention == null) {
			if (other.intervention != null)
				return false;
		} else if (!intervention.equals(other.intervention))
			return false;
		if (sparePart == null) {
			if (other.sparePart != null)
				return false;
		} else if (!sparePart.equals(other.sparePart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sustitucion [sparePart=" + sparePart 
				+ ", intervention=" + intervention 
				+ ", quantity=" + quantity + "]";
	}

	void _setRepuesto(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervencion(Intervention intervention) {
		this.intervention = intervention;
	}

	public double getImporte() {
		return quantity * sparePart.getPrecio();
	}

}
