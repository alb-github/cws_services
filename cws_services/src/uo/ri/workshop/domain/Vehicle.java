package uo.ri.workshop.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.Argument;

public class Vehicle {
	private Long id;

	private String plateNumber;
	private String mark;
	private String model;

	private Client client;
	private VehicleType vehicleType;
	private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

	Vehicle() {	}

	public Vehicle(String plateNumber) {
		super();
		Argument.isNotEmpty(plateNumber);
		this.plateNumber = plateNumber;
	}

	public Vehicle(String plateNumber, String mark, String model) {
		this(plateNumber);
		setMarck(mark);
		setModel(model);
	}

	public Long getId() {
		return id;
	}

	public String getMark() {
		return mark;
	}

	public void setMarck(String mark) {
		Argument.isNotEmpty(mark);
		this.mark = mark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		Argument.isNotEmpty(model);
		this.model = model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((plateNumber == null) ? 0 : plateNumber.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (plateNumber == null) {
			if (other.plateNumber != null)
				return false;
		} else if (!plateNumber.equals(other.plateNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehiculo [mark=" + mark 
				+ ", plateNumber=" + plateNumber
				+ ", model=" + model 
				+ "]";
	}

	public Client getClient() {
		return client;
	}

	/* package */ void _setClient(Client client) {
		this.client = client;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setVehicleType(VehicleType tipo) {
		this.vehicleType = tipo;
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	/* package */ void _addWorkOrder(WorkOrder a) {
		this.workOrders.add(a);
	}

	/* package */ void _removeWorkOrder(WorkOrder a) {
		this.workOrders.remove(a);
	}

	public double getPricePerHour() {
		return getVehicleType().getPricePerHour();
	}
}
