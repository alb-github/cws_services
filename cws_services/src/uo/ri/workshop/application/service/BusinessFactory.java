package uo.ri.workshop.application.service;

import uo.ri.workshop.application.ServiceFactory;
import uo.ri.workshop.application.service.invoice.InvoiceService;
import uo.ri.workshop.application.service.invoice.InvoiceServiceImpl;
import uo.ri.workshop.application.service.mechanic.MechanicCrudService;
import uo.ri.workshop.application.service.mechanic.MechanicCrudServiceImpl;
import uo.ri.workshop.application.service.vehicle.VehicleReceptionService;
import uo.ri.workshop.application.service.workorder.CloseWorkOrderService;

public class BusinessFactory implements ServiceFactory {
	
	@Override
	public MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	@Override
	public InvoiceService forInvoice() {
		return new InvoiceServiceImpl();
	}

	@Override
	public VehicleReceptionService forVehicleReception() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public CloseWorkOrderService forClosingBreakdown() {
		throw new RuntimeException("Not yet implemented");
	}

}
