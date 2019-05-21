package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.SparePart;
import uo.ri.workshop.domain.Substitution;
import uo.ri.workshop.domain.Vehicle;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.WorkOrder;
import uo.ri.workshop.domain.WorkOrder.WorkOrderStatus;


public class InvoiceTests {
	private Mechanic mecanico;
	private WorkOrder averia;
	private Intervention intervencion;
	private SparePart repuesto;
	private Vehicle vehiculo;
	private VehicleType tipoVehiculo;
	private Client cliente;
	private Invoice factura;

	@Before
	public void setUp() {
		cliente = new Client("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		Associations.Own.link(cliente, vehiculo );

		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);
		
		averia = new WorkOrder(vehiculo, "falla la junta la trocla");
		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
		
		averia.assignTo(mecanico); // averia pasa a asignada
	
		intervencion = new Intervention(mecanico, averia, 60);
		
		repuesto = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(repuesto, intervencion, 2);

		averia.markAsFinished(); // changes status & compute price
		
		factura = new Invoice(0L, Dates.today());
		factura.addWorkOrder(averia);
	}
	
	@Test
	public void testFacturarLinked() {
		assertTrue( factura.getWorkOrders().contains( averia ));
		assertTrue( factura.getAmount() > 0.0 );

		assertTrue( averia.getInvoice() == factura);
		assertTrue( averia.getStatus().equals( WorkOrderStatus.INVOICED ) );
	}

	@Test
	public void testFacturarUnlink() {
		factura.removeWorkOrder(averia);
		
		assertTrue( ! factura.getWorkOrders().contains( averia ));
		assertTrue( factura.getWorkOrders().size() == 0 );
		assertTrue( factura.getAmount() == 0.0 );
		
		assertTrue( averia.getInvoice() == null);
		assertTrue( averia.getStatus().equals( WorkOrderStatus.FINISHED ) );
	}
	
	@Test
	public void testSafeReturn() {
		Set<WorkOrder> facturadas = factura.getWorkOrders();
		facturadas.remove( averia );

		assertTrue( facturadas.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			factura.getWorkOrders().size() == 1
		);
	}
	
}
