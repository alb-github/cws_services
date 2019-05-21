package uo.ri.workshop.domain;

public class Cash extends PaymentMean {

	Cash() {}
	
	public Cash(Client client) {
		super();
		Associations.Pay.link(this, client);
	}

	@Override
	public String toString() {
		return "Cash [accumulated=" + getAcummulated() 
			+ ", client=" + getClient() + "]";
	}
	
}
