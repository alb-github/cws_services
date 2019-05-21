package uo.ri.workshop.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Intervention {
	private Long id;
	private WorkOrder workOrder;
	private Mechanic mechanic;
	private Date date;
	private int minutes;
	
	private Set<Substitution> substitutions = new HashSet<Substitution>();
	
	Intervention() { }

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		date = new Date();
		Associations.Intervene.link(workOrder, this, mechanic);
		this.minutes = minutes;
	}

	public Long getId() {
		return id;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((mechanic == null) ? 0 : mechanic.hashCode());
		result = prime * result
				+ ((workOrder == null) ? 0 : workOrder.hashCode());
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
		Intervention other = (Intervention) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (mechanic == null) {
			if (other.mechanic != null)
				return false;
		} else if (!mechanic.equals(other.mechanic))
			return false;
		if (workOrder == null) {
			if (other.workOrder != null)
				return false;
		} else if (!workOrder.equals(other.workOrder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Intervention [workOrder=" + workOrder 
				+ ", mechanic=" + mechanic
				+ ", date=" + date
				+ ", minutes=" + minutes 
				+ "]";
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>( substitutions );
	}
	
	Set<Substitution> _getSustitutions() {
		return substitutions;
	}

	public double getAmount() {
		return getWorkAmount() + getSparesAmount();
	}

	private double getWorkAmount() {
		double pricePerHour = getWorkOrder().getPricePerHour();
		return minutes * pricePerHour / 60.0;
	}

	private double getSparesAmount() {
		double total = 0.0;
		for(Substitution s: substitutions) {
			total += s.getImporte();
		}
		return total;
	}
	
}
