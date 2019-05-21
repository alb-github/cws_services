package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.SparePart;
import uo.ri.workshop.domain.Substitution;
import uo.ri.workshop.domain.Vehicle;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.WorkOrder;


public class SustitutionTests {
	
	private Mechanic mecanico;
	private WorkOrder averia;
	private Intervention intervencion;
	private Vehicle vehiculo;
	private VehicleType tipoVehiculo;
	private Client cliente;

	@Before
	public void setUp() {
		cliente = new Client("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		Associations.Own.link(cliente, vehiculo);

		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);

		averia = new WorkOrder(vehiculo, "falla la junta la trocla");
		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
	
		intervencion = new Intervention(mecanico, averia, 0);
	}
	
	/**
	 * Sustitución con menos de 1 repuesto lanza IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSustitucionThrowsExceptionIfZero() {
		SparePart r = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(r, intervencion, 0);
	}
	
	/**
	 * Sustitución con menos de 1 repuesto lanza IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSustitucionThrowsExceptionIfNegative() {
		SparePart r = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(r, intervencion, -1);
	}
	
	/**
	 * Importe correcto de la sustitución con varios repuestos
	 */
	@Test
	public void testImporteSustitucion() {
		SparePart r = new SparePart("R1001", "junta la trocla", 100.0);
		Substitution s = new Substitution(r, intervencion, 2);

		assertTrue( s.getImporte() == 200.0 );
	}

}
