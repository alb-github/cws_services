package uo.ri.workshop.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alb.util.assertion.Argument;
import alb.util.assertion.StateCheck;
import alb.util.date.Dates;
import alb.util.math.Round;

public class Invoice {
	public enum InvoiceStatus { NOT_YET_PAID, PAID }

	private Long id;
	
	private Long number;
	private Date date;
	private double amount;
	private double vat;
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	private Set<WorkOrder> workOrders = new HashSet<>();
	private Set<Charge> charges = new HashSet<>();

	Invoice() {}
	
	public Invoice(Long number, Date date) {
		super();
		Argument.isNotNull( number );
		this.number = number;
		this.date = new Date( date.getTime() ); // A copy as Date is mutable
	}

	public Invoice(Long number) {
		this(number, new Date());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, new Date(), workOrders);
	}

	public Invoice(Long number, Date date, List<WorkOrder> workOrders) {
		this( number, date );
		for(WorkOrder a: workOrders) {
			addWorkOrder( a );
		}
	}

	public Date getDate() {
		return new Date( date.getTime() );
	}

	public void setDate(Date date) {
		this.date = new Date( date.getTime() );   // A copy
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		computeAmount();
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public Long getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Invoice other = (Invoice) obj;
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!number.equals(other.number)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number 
				+ ", date=" + date 
				+ ", amount=" + getAmount() 
				+ ", vat=" + vat 
				+ ", status=" + status + "]";
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	private void computeAmount() {
		amount = 0.0;
		for(WorkOrder a: workOrders) {
			amount += a.getAmount();
		}
		vat = amount * getVatType();
		amount += vat;
		amount = Round.twoCents( amount ); 
	}

	private double getVatType() {
		Date JULY_1_2012 = Dates.fromDdMmYyyy(1, 7, 2012);
		return date.after( JULY_1_2012 ) ? 0.21 : 0.18;
	}

	public void addWorkOrder(WorkOrder workOrder) {
		StateCheck.isTrue( isNotSettled(), "The invoice is not settled" );
		StateCheck.isTrue( workOrder.isFinished(), "The breakdown is not finished" );
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	public void removeWorkOrder(WorkOrder workOrder) {
		StateCheck.isTrue( isNotSettled(), "The invoice is not settled" );
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		computeAmount();
	}

	public boolean isNotSettled() {
		return InvoiceStatus.NOT_YET_PAID.equals( status );
	}

	public boolean isSettled() {
		return InvoiceStatus.PAID.equals( status );
	}

	/**
	 * Marks the invoice as ABONADA, but
	 * @throws IllegalStateException if
	 * 	- Is already settled 
	 *  - Or the amounts paid with charges to payment means do not cover 
	 *  	the total of the invoice  
	 */
	public void settle() {
		StateCheck.isTrue( isNotSettled(), "The invoice is not settled" );
		StateCheck.isTrue( chargesAmountsInvoiceTotal(), "" );
		status = InvoiceStatus.PAID;
	}

	private boolean chargesAmountsInvoiceTotal() {
		return Math.abs( computeAmountOfCharges() - amount) <= 0.01;
	}

	private double computeAmountOfCharges() {
		double total = 0.0;
		for(Charge c: charges) {
			total += c.getAmount();
		}
		return total;
	}

	public Long getId() {
		return id;
	}

}
