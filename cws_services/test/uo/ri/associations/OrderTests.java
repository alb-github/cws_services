package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.WorkOrder;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.Vehicle;


public class OrderTests {
	private WorkOrder averia;
	private Vehicle vehiculo;
	private VehicleType tipoVehiculo;
	private Client cliente;

	@Before
	public void setUp() {
		cliente = new Client("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		Associations.Own.link(cliente, vehiculo );

		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);
		
		averia = new WorkOrder(vehiculo, "falla la junta la trocla");
	}
	
	@Test
	public void testAveriarLinked() {
		// The constructor of "Averia" creates the link with "vehiculo"
		// It calls Association.Averiar.link(...)
		assertTrue( vehiculo.getWorkOrders().contains( averia ));
		assertTrue( averia.getVehicle() == vehiculo );
	}

	@Test
	public void testAveriarUnlink() {
		Associations.Order.unlink(vehiculo, averia);
		
		assertTrue( ! vehiculo.getWorkOrders().contains( averia ));
		assertTrue( averia.getVehicle() == null );
	}

	@Test
	public void testAveriarUnlinkTwice() {
		Associations.Order.unlink(vehiculo, averia);
		Associations.Order.unlink(vehiculo, averia);
		
		assertTrue( ! vehiculo.getWorkOrders().contains( averia ));
		assertTrue( averia.getVehicle() == null );
	}

	@Test
	public void testSafeReturn() {
		Set<WorkOrder> averias = vehiculo.getWorkOrders();
		averias.remove( averia );

		assertTrue( averias.size() == 0 );
		assertTrue( "Se tiene que retornar copia de la coleccion", 
			vehiculo.getWorkOrders().size() == 1
		);
	}



}
