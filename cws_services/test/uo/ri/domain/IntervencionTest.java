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

public class IntervencionTest {
	
	private Mechanic mecanico;
	private WorkOrder averia;
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
	}
	
	/**
	 * Una intervencion sin tiempo asignado ni repuestos da como precio 0 €
	 */
	@Test
	public void testAmountsZero() {
		Intervention i = new Intervention(mecanico, averia, 0);

		assertTrue( i.getAmount() == 0.0 );
	}

	/**
	 * Intervencion con 60 minutos da importe del precio hora
	 */
	@Test
	public void testImporteHora() {
		Intervention i = new Intervention(mecanico, averia, 60);
		
		assertTrue( i.getAmount() == tipoVehiculo.getPricePerHour() );
	}
	
	/**
	 * Intervención con un solo repuesto da importe del respuesto
	 */
	@Test
	public void testImporteRepuesto() {
		Intervention i = new Intervention(mecanico, averia, 0);
		SparePart r = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(r, i, 1);
		
		assertTrue( i.getAmount() == r.getPrecio() );
	}
	
	/**
	 * Intervención con tiempo y repuestos da el importe debido
	 */
	@Test
	public void testImporteIntervencionCompleta() {
		Intervention i = new Intervention(mecanico, averia, 60);
		
		SparePart r = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(r, i, 2);
		
		final double TOTAL = 
					   50.0  // 60 mins * 50 €/hora tipo vehiculo 
				+ 2 * 100.0; // 2 repuestos a 100.0

		assertTrue( i.getAmount() == TOTAL );
	}

}
