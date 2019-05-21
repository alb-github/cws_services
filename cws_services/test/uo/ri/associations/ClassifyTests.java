package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.Vehicle;


public class ClassifyTests {
	private Vehicle vehiculo;
	private VehicleType tipoVehiculo;

	@Before
	public void setUp() {
		vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);
	}
	
	@Test
	public void testClasificarLinked() {
		assertTrue( tipoVehiculo.getVehicles().contains( vehiculo ));
		assertTrue( vehiculo.getVehicleType() == tipoVehiculo );
	}

	@Test
	public void testClasificarUnlink() {
		Associations.Classify.unlink(tipoVehiculo, vehiculo);

		assertTrue( ! tipoVehiculo.getVehicles().contains( vehiculo ));
		assertTrue( vehiculo.getVehicleType() == null );
	}

	@Test
	public void testSafeReturn() {
		Set<Vehicle> vehiculos = tipoVehiculo.getVehicles();
		vehiculos.remove( vehiculo );

		assertTrue( vehiculos.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			tipoVehiculo.getVehicles().size() == 1
		);
	}

}
