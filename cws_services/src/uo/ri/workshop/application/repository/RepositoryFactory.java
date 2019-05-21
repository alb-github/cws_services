package uo.ri.workshop.application.repository;

public interface RepositoryFactory {

	MechanicRepository forMechanic();
	WorkOrderRepository forWorkOrder();
	PaymentMeanRepository forPaymentMean();
	InvoiceRepository forInvoice();
	ClientRepository forClient();
	SparePartRepository forSparePart();
	InterventionRepository forIntervention();

}
