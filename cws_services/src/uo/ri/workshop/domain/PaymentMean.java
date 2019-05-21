package uo.ri.workshop.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class PaymentMean {
	private Long id;
	private double accumulated = 0.0;
	
	private Client client;
	private Set<Charge> charges = new HashSet<>();

	public void pay(double importe) {
		this.accumulated += importe;
	}

	public double getAcummulated() {
		return accumulated;
	}

	public Client getClient() {
		return client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
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
		PaymentMean other = (PaymentMean) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		return true;
	}

	void _setClient(Client client) {
		 this.client = client;
	}

	Set<Charge> _getCharges() {
		return charges ;
	}
	
	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	public Long getId() {
		return id;
	}

}
