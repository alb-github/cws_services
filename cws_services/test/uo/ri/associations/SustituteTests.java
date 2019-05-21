package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

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


public class SustituteTests {
	private Mechanic mecanico;
	private WorkOrder averia;
	private Intervention intervencion;
	private SparePart repuesto;
	private Substitution sustitucion;
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
	
		intervencion = new Intervention(mecanico, averia, 60);
		
		repuesto = new SparePart("R1001", "junta la trocla", 100.0);
		sustitucion = new Substitution(repuesto, intervencion, 2);
	}
	
	@Test
	public void testSustituirAdd() {
		assertTrue( sustitucion.getIntervention().equals( intervencion ));
		assertTrue( sustitucion.getSparePart().equals( repuesto ));
		
		assertTrue( repuesto.getSustituciones().contains( sustitucion ));
		assertTrue( intervencion.getSustitutions().contains( sustitucion ));
	}

	@Test
	public void testSustituirRemove() {
		Associations.Sustitute.unlink( sustitucion );
		
		assertTrue( sustitucion.getIntervention() == null);
		assertTrue( sustitucion.getSparePart() == null);
		
		assertTrue( ! repuesto.getSustituciones().contains( sustitucion ));
		assertTrue( repuesto.getSustituciones().size() == 0 );

		assertTrue( ! intervencion.getSustitutions().contains( sustitucion ));
		assertTrue( intervencion.getSustitutions().size() == 0 );
	}

	@Test
	public void testSafeReturnIntervencion() {
		Set<Substitution> sustituciones = intervencion.getSustitutions();
		sustituciones.remove( sustitucion );

		assertTrue( sustituciones.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			intervencion.getSustitutions().size() == 1
		);
	}

	@Test
	public void testSafeReturnRepuesto() {
		Set<Substitution> sustituciones = repuesto.getSustituciones();
		sustituciones.remove( sustitucion );

		assertTrue( sustituciones.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			repuesto.getSustituciones().size() == 1
		);
	}

}
