package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.Vehicle;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.WorkOrder;


public class IntervenirTest {
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
		Associations.Own.link(cliente, vehiculo );

		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);
		
		averia = new WorkOrder(vehiculo, "falla la junta la trocla");

		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
	
		intervencion = new Intervention(mecanico, averia, 60);
	}
	
	@Test
	public void testArreglarAdd() {
		assertTrue( averia.getInterventions().contains( intervencion ));
		assertTrue( intervencion.getWorkOrder() == averia );
	}

	@Test
	public void testArreglarRemove() {
		Associations.Intervene.unlink(intervencion);
		
		assertTrue( ! averia.getInterventions().contains( intervencion ));
		assertTrue( averia.getInterventions().size() == 0 );
		assertTrue( intervencion.getWorkOrder() == null );
	}

	@Test
	public void testTrabajarAdd() {
		assertTrue( mecanico.getInterventions().contains( intervencion ));
		assertTrue( intervencion.getMechanic() == mecanico );
	}

	@Test
	public void testTrabajarRemove() {
		Associations.Intervene.unlink(intervencion);
		
		assertTrue( ! mecanico.getInterventions().contains( intervencion ));
		assertTrue( mecanico.getInterventions().size() == 0 );
		assertTrue( intervencion.getMechanic() == null );
	}

	@Test
	public void testSafeReturnMecanico() {
		Set<Intervention> intervenciones = mecanico.getInterventions();
		intervenciones.remove( intervencion );

		assertTrue( intervenciones.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			mecanico.getInterventions().size() == 1
		);
	}

	@Test
	public void testSafeReturnRepuesto() {
		Set<Intervention> intervenciones = averia.getInterventions();
		intervenciones.remove( intervencion );

		assertTrue( intervenciones.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			averia.getInterventions().size() == 1
		);
	}



}
