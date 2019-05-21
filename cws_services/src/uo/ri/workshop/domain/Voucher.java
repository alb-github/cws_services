package uo.ri.workshop.domain;

import alb.util.assertion.Argument;
import alb.util.assertion.StateCheck;

public class Voucher extends PaymentMean {
	private String code;
	private double available = 0.0;
	private String description;
	
	Voucher(){}
	
	public Voucher(String codigo) {
		super();
		Argument.isNotEmpty( codigo );
		this.code = codigo;
	}
	
	public Voucher(String codigo, double available) {
		this( codigo );
		this.available = available;
	}

	public Voucher(String codigo, double available, String description) {
		this( codigo, available );
		this.description = description;
	}
	
	@Override
	public void pay(double amount) {
		StateCheck.isTrue( available >= amount, "Not enough available");
		super.pay(amount);
		this.available -= amount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Voucher other = (Voucher) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}

	public double getDisponible() {
		return available;
	}

	public String getDescripcion() {
		return description;
	}

	public void setDescripcion(String description) {
		this.description = description;
	}

	public String getCodigo() {
		return code;
	}

	@Override
	public String toString() {
		return "Voucher [description=" + description 
				+ ", code=" + code 
				+ ", available=" + available 
				+ "]";
	}
	
}
