package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.WorkOrder;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.Vehicle;


public class AssignTests {
	private Mechanic mecanico;
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

		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
		Associations.Assign.link(mecanico, averia);
	}
	
	@Test
	public void testAsignarLinked() {
		assertTrue( mecanico.getAssigned().contains( averia ));
		assertTrue( averia.getMechanic() == mecanico );
	}

	@Test
	public void testAsignarUnlink() {
		Associations.Assign.unlink(mecanico, averia );
		
		assertTrue( ! mecanico.getAssigned().contains( averia ));
		assertTrue( mecanico.getAssigned().size() == 0 );
		assertTrue( averia.getMechanic() == null );
	}

	@Test
	public void testSafeReturn() {
		Set<WorkOrder> asignadas = mecanico.getAssigned();
		asignadas.remove( averia );

		assertTrue( asignadas.size() == 0 );
		assertTrue( "Se tiene que retornar copia de la colección", 
			mecanico.getAssigned().size() == 1
		);
	}

}
