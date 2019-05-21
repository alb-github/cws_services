package uo.ri.workshop.application;

import uo.ri.workshop.application.service.invoice.InvoiceService;
import uo.ri.workshop.application.service.mechanic.MechanicCrudService;
import uo.ri.workshop.application.service.vehicle.VehicleReceptionService;
import uo.ri.workshop.application.service.workorder.CloseWorkOrderService;

public interface ServiceFactory {

	// Admin services
	MechanicCrudService forMechanicCrudService();

	// Cash use cases
	InvoiceService forInvoice();
	
	// Foreman use cases
	VehicleReceptionService forVehicleReception();


	// Mechanic services
	CloseWorkOrderService forClosingBreakdown();

}
