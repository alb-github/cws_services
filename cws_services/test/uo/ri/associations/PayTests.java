package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Cash;
import uo.ri.workshop.domain.Charge;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.SparePart;
import uo.ri.workshop.domain.Substitution;
import uo.ri.workshop.domain.Vehicle;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.WorkOrder;


public class PayTests {
	private Mechanic mecanico;
	private WorkOrder averia;
	private Intervention intervencion;
	private SparePart repuesto;
	private Vehicle vehiculo;
	private VehicleType tipoVehiculo;
	private Client cliente;
	private Invoice factura;
	private Cash metalico;
	private Charge cargo;

	@Before
	public void setUp() {
		cliente = new Client("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		Associations.Own.link(cliente, vehiculo );

		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);
		
		averia = new WorkOrder(vehiculo, "falla la junta la trocla");
		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);
	
		intervencion = new Intervention(mecanico, averia, 60);
		
		repuesto = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(repuesto, intervencion, 2);
		
		averia.markAsFinished();

		factura = new Invoice(0L, Dates.today());
		factura.addWorkOrder(averia);
		
		metalico = new Cash( cliente );
		cargo = new Charge(factura, metalico, 100.0);
	}
	
	@Test
	public void testPagarAdd() {
		assertTrue( cliente.getPaymentMeans().contains( metalico ));
		assertTrue( metalico.getClient() == cliente );
	}

	@Test
	public void testPagarRemove() {
		Associations.Pay.unlink(cliente, metalico);
		
		assertTrue( ! cliente.getPaymentMeans().contains( metalico ));
		assertTrue( cliente.getPaymentMeans().size() == 0 );
		assertTrue( metalico.getClient() == null );
	}

	@Test
	public void testCargarAdd() {
		assertTrue( metalico.getCharges().contains( cargo ));
		assertTrue( factura.getCharges().contains( cargo ));
		
		assertTrue( cargo.getInvoice() == factura );
		assertTrue( cargo.getPaymentMean() == metalico );
		
		assertTrue( metalico.getAcummulated() == 100.0 );
	}

	@Test
	public void testCargarRemove() {
		Associations.Charges.unlink( cargo );
		
		assertTrue( ! metalico.getCharges().contains( cargo ));
		assertTrue( metalico.getCharges().size() == 0 );

		assertTrue( ! factura.getCharges().contains( cargo ));
		assertTrue( metalico.getCharges().size() == 0 );
		
		assertTrue( cargo.getInvoice() == null );
		assertTrue( cargo.getPaymentMean() == null );
	}

}
