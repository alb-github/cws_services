package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Vehicle;


public class OnwTests {
	private Vehicle vehiculo;
	private Client cliente;

	@Before
	public void setUp() {
		cliente = new Client("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		Associations.Own.link(cliente, vehiculo);
	}
	
	@Test
	public void testPoseerAdd() {
		assertTrue( cliente.getVehicles().contains( vehiculo ));
		assertTrue( vehiculo.getClient() == cliente );
	}

	@Test
	public void testPoseerRemove() {
		Associations.Own.unlink(cliente, vehiculo);

		assertTrue( ! cliente.getVehicles().contains( vehiculo ));
		assertTrue( vehiculo.getClient() == null );
	}

	@Test
	public void testSafeReturn() {
		Set<Vehicle> vehiculos = cliente.getVehicles();
		vehiculos.remove( vehiculo );

		assertTrue( vehiculos.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			cliente.getVehicles().size() == 1
		);
	}


}
