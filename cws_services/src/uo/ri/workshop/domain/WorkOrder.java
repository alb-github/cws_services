package uo.ri.workshop.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.StateCheck;

public class WorkOrder {
	public static enum WorkOrderStatus {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED		
	}
	
	private Long id;
	private String description;
	private Date date;
	private double amount = 0.0;
	private WorkOrderStatus status = WorkOrderStatus.OPEN;
	
	private Invoice invoice;
	private Mechanic mechanic;
	private Vehicle vehicle;
	protected Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {}
	
	public WorkOrder(Vehicle vehicle) {
		super();
		this.date = new Date();
		Associations.Order.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this( vehicle );
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descripcion) {
		this.description = descripcion;
	}

	public double getAmount() {
		return amount;
	}

	public void computeAmount() {
		amount = 0.0;
		for(Intervention i: interventions) {
			amount += i.getAmount();
		}
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Date getDate() {
		return new Date( date.getTime() );
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WorkOrder other = (WorkOrder) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (vehicle == null) {
			if (other.vehicle != null) {
				return false;
			}
		} else if (!vehicle.equals(other.vehicle)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "WorkOrder [description=" + description 
				+ ", date=" + date 
				+ ", amount=" + amount 
				+ ", status=" + status
				+ "]";
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions  );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public boolean isFinished() {
		return WorkOrderStatus.FINISHED.equals( status );
	}

	public boolean isAssigned() {
		return WorkOrderStatus.ASSIGNED.equals( status );
	}
	
	public boolean isInvoiced() {
		return WorkOrderStatus.INVOICED.equals( status );
	}

	public boolean isOpen() {
		return WorkOrderStatus.OPEN.equals( status );
	}

	public void markAsInvoiced() {
		StateCheck.isTrue( isFinished(), "Work order not finished" );
		StateCheck.isTrue( invoice != null, "No invoice assigned" );
		status = WorkOrderStatus.INVOICED;
	}

	public void markAsFinished() {
		StateCheck.isTrue( isAssigned(), "Work order not assigned" );
		Associations.Assign.unlink(mechanic, this);
		computeAmount();
		status = WorkOrderStatus.FINISHED;
	}

	public void markBackToFinished() {
		StateCheck.isTrue( isInvoiced(), "Work order not invoiced" );
		status = WorkOrderStatus.FINISHED;
	}

	public void assignTo(Mechanic mechanic) {
		StateCheck.isTrue( isOpen(), "Work order not opened" );
		Associations.Assign.link(mechanic, this);
		status = WorkOrderStatus.ASSIGNED;
	}

	public void desassign() {
		StateCheck.isTrue( isAssigned(), "Work order not assigned" );
		Associations.Assign.unlink(mechanic, this);
		status = WorkOrderStatus.OPEN;
	}

	public void reopen() {
		StateCheck.isTrue( isFinished(), "Work order not finished" );
		status = WorkOrderStatus.OPEN;
	}

	public Long getId() {
		return id;
	}

	public double getPricePerHour() {
		return getVehicle().getPricePerHour();
	}
}
