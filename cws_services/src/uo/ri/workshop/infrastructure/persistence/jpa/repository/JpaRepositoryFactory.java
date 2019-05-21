package uo.ri.workshop.infrastructure.persistence.jpa.repository;

import uo.ri.workshop.application.repository.WorkOrderRepository;
import uo.ri.workshop.application.repository.ClientRepository;
import uo.ri.workshop.application.repository.InvoiceRepository;
import uo.ri.workshop.application.repository.InterventionRepository;
import uo.ri.workshop.application.repository.MechanicRepository;
import uo.ri.workshop.application.repository.PaymentMeanRepository;
import uo.ri.workshop.application.repository.RepositoryFactory;
import uo.ri.workshop.application.repository.SparePartRepository;

public class JpaRepositoryFactory implements RepositoryFactory {

	@Override
	public MechanicRepository forMechanic() {
		return new MechanicJpaRepository();
	}

	@Override
	public WorkOrderRepository forWorkOrder() {
		return new WorkOrderJpaRepository();
	}

	@Override
	public PaymentMeanRepository forPaymentMean() {
		return new PaymentMeanJpaRepository();
	}

	@Override
	public InvoiceRepository forInvoice() {
		return new InvoiceJpaRepository();
	}

	@Override
	public ClientRepository forClient() {
		return new ClientJpaRepository();
	}

	@Override
	public SparePartRepository forSparePart() {
		return new SparePartJpaRepository();
	}

	@Override
	public InterventionRepository forIntervention() {
		return new InterventionJpaRepository();
	}

}
