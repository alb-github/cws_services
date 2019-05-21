package uo.ri.workshop.domain;

import alb.util.assertion.StateCheck;

public class Charge {
	private Long id;
	private Invoice invoice;
	private PaymentMean paymentMean;
	private double amount = 0.0;
	
	Charge() {}
	
	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		super();
		paymentMean.pay(amount);
		this.amount = amount;
		Associations.Charges.link(invoice, this, paymentMean);
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((paymentMean == null) ? 0 : paymentMean.hashCode());
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
		Charge other = (Charge) obj;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (paymentMean == null) {
			if (other.paymentMean != null)
				return false;
		} else if (!paymentMean.equals(other.paymentMean))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Charge [invoice=" + invoice 
				+ ", paymentMean=" + paymentMean 
				+ ", amount=" + amount + "]";
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		StateCheck.isTrue( invoice.isNotSettled() );
		paymentMean.pay( -amount );
		Associations.Charges.unlink( this );
	}
	
	public Long getId() {
		return id;
	}
	
}
