package uo.ri.workshop.domain;

import java.util.Date;

import alb.util.assertion.Argument;
import alb.util.assertion.StateCheck;

public class CreditCard extends PaymentMean {
	private String number;
	private String type;
	private Date validThru;

	CreditCard() {}

	public CreditCard(String number, String type, Date validThru) {
		super();
		Argument.isTrue(number != null && number.length() > 0);
		Argument.isTrue(type != null && type.length() > 0);
		this.number = number;
		this.type = type;
		this.validThru = new Date(validThru.getTime());
	}

	public String getType() {
		return type;
	}

	public Date getValidThru() {
		return new Date(validThru.getTime());
	}

	public String getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCard [numero=" + number 
				+ ", tipo=" + type 
				+ ", validez=" + validThru + "]";
	}

	@Override
	public void pay(double importe) {
		StateCheck.isTrue(isValidNow(), "The card is timed out");
		super.pay(importe);
	}

	public boolean isValidNow() {
		Date now = new Date();
		return (validThru.after(now) || validThru.equals(now));
	}

}
