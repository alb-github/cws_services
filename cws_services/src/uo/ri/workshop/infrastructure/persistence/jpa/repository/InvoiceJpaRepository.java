package uo.ri.workshop.infrastructure.persistence.jpa.repository;

import uo.ri.workshop.application.repository.InvoiceRepository;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.infrastructure.persistence.jpa.util.BaseRepository;
import uo.ri.workshop.infrastructure.persistence.jpa.util.Jpa;

public class InvoiceJpaRepository 
		extends BaseRepository<Invoice>
		implements InvoiceRepository {

	@Override
	public Invoice findByNumber(Long numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		return Jpa.getManager()
				.createNamedQuery("Invoice.getNextInvoiceNumber", Long.class)
				.getSingleResult();
	}

}
