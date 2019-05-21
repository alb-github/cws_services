package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.ri.workshop.application.service.BusinessException;
import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.domain.Invoice.InvoiceStatus;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.SparePart;
import uo.ri.workshop.domain.Substitution;
import uo.ri.workshop.domain.Vehicle;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.WorkOrder;


/**
 * Para entender mejor estos test repasa el diagrama de estados de una
 * avería en la documentación del problema de referencia
 */
public class WorkOrderTests {
	
	private Mechanic mecanico;
	private WorkOrder averia;
	private Intervention intervencion;
	private SparePart repuesto;
	private Vehicle vehiculo;
	private VehicleType tipoVehiculo;
	private Client cliente;

	@Before
	public void setUp() {
		cliente = new Client("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehicle("1234 GJI", "ibiza", "seat");
		Associations.Own.link(cliente, vehiculo);

		tipoVehiculo = new VehicleType("coche", 50.0 /* €/hora */);
		Associations.Classify.link(tipoVehiculo, vehiculo);

		averia = new WorkOrder(vehiculo, "falla la junta la trocla");
		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
		averia.assignTo( mecanico );
	
		intervencion = new Intervention(mecanico, averia, 60);
		
		repuesto = new SparePart("R1001", "junta la trocla", 100.0 /* € */);
		new Substitution(repuesto, intervencion, 2);
		
		averia.markAsFinished(); // changes status & computes price
	}
	
	/**
	 * El importe de la averia de referencia es 250.0
	 */
	@Test
	public void testImporteAveria() {
		assertTrue( averia.getAmount() == 250.0 );
	}

	/**
	 * Calculo del importe de averia con intervenciones de varios mecanicos
	 */
	@Test
	public void testImporteAveriaConDosIntervenciones() {
		averia.reopen();  // changes from TERMINADA to ABIERTA again
		Mechanic otro = new Mechanic("1", "a", "n");
		averia.assignTo( otro );
		new Intervention(otro, averia, 30);
		
		averia.markAsFinished();
		
		assertTrue( averia.getAmount() == 275.0 );
	}

	/**
	 * Cálculo correcto de importe de avería al quitar intervenciones
	 * El (re)cálculo se hace al pasar la avería a TERMINADA 
	 */
	@Test
	public void testImporteAveriaQuitandoIntervencione() {
		averia.reopen();
		Mechanic otro = new Mechanic("1", "a", "n");
		averia.assignTo( otro );
		new Intervention(otro, averia, 30);
		
		Associations.Intervene.unlink( intervencion );
		averia.markAsFinished();
		
		assertTrue( averia.getAmount() == 25.0 );
	}

	/**
	 * No se puede añadir a una factura una averia no terminada
	 * y lo indica lanzando una IllegalStateException
	 * @throws IllegalStateException
	 */
	@Test( expected = IllegalStateException.class )
	public void testAveriaNoTerminadaException() {
		averia.reopen();
		List<WorkOrder> averias = new ArrayList<WorkOrder>();
		averias.add( averia );
		new Invoice( 0L,  averias ); // debe saltar IllegalStateExcepcion: averia no terminada
	}

	/**
	 * Una factura creada y con averias asignadas está en estado SIN_ABONAR
	 * @throws BusinessException
	 */
	@Test
	public void testFacturaCreadaSinAbonar() {
		List<WorkOrder> averias = new ArrayList<WorkOrder>();
		averias.add( averia );
		Invoice factura = new Invoice( 0L, averias );
		
		assertTrue( factura.getStatus() ==  InvoiceStatus.NOT_YET_PAID );
	}

	/**
	 * Una averia no puede ser marcada como facturada si no tiene factura 
	 * asignada
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testSinFacturaNoMarcarFacturada() {
		averia.markAsInvoiced();  // Lanza excepción "No factura asignada"
	}
	
	/**
	 * La fecha devuelta por una avería debe ser una copia de la interna
	 */
	@Test
	public void testGetDateReturnsCopy() {
		Date one = averia.getDate();
		Date other = averia.getDate();
		
		assertTrue( one != other );
		assertTrue( one.equals( other ) );
	}

}
