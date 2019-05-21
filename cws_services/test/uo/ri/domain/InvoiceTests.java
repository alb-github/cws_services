package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
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
import uo.ri.workshop.domain.WorkOrder.WorkOrderStatus;


public class InvoiceTests {
	
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

		tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);

		averia = new WorkOrder(vehiculo, "falla la junta la trocla");
		mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);
	
		intervencion = new Intervention(mecanico, averia, 60);
		
		repuesto = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(repuesto, intervencion, 2);
		
		averia.markAsFinished();
	}
	
	/**
	 * Calculo del importe de factura con una avería de 260€ + IVA 21%
	 * La averia se añade en el constructor  
	 */
	@Test
	public void testImporteFactura() {
		List<WorkOrder> averias = new ArrayList<>();
		averias.add( averia );
		Invoice factura = new Invoice( 0L, averias );
		
		assertTrue( factura.getAmount() ==  302.5 );
	}

	/**
	 * Calculo del importe de factura con una avería de 260€ + IVA 21%
	 * La averia se añade por asociación  
	 */
	@Test
	public void testImporteFacturaAddAveria() {
		Invoice factura = new Invoice( 0L ); // 0L es el numero de factura
		factura.addWorkOrder(averia);
		
		assertTrue( factura.getAmount() ==  302.5 );
	}

	/**
	 * Dos averias añadidas a la factura en el constructor
	 */
	@Test
	public void testImporteFacturadDosAverias() {
		List<WorkOrder> averias = new ArrayList<WorkOrder>();
		averias.add( averia );
		averias.add( crearOtraAveria() );
		Invoice factura = new Invoice( 0L, averias );
		
		// importe = (137.5 nueva averia + 250.0 primera averia) * 21% iva
		assertTrue( factura.getAmount() ==  468.88 ); // redondeo a 2 céntimos
	}

	/**
	 * Dos averias añadidas a la factura por asociación
	 */
	@Test
	public void testImporteFacturaAddDosAverias() {
		Invoice factura = new Invoice( 0L );
		factura.addWorkOrder( averia );
		factura.addWorkOrder( crearOtraAveria() );
		
		assertTrue( factura.getAmount() ==  468.88 ); // redondeo a 2 céntimos
	}

	/**
	 * Una factura creada y con averías está SIN_ABONAR
	 */
	@Test
	public void testFacturaCreadaSinAbonar() {
		List<WorkOrder> averias = new ArrayList<WorkOrder>();
		averias.add( averia );
		Invoice factura = new Invoice( 0L, averias );
		
		assertTrue( factura.getStatus() ==  InvoiceStatus.NOT_YET_PAID );
	}

	/**
	 * Si la factura es anterior al 1/7/2012 el IVA es el 18%, 
	 * el importe es 250€ + IVA 18%
	 */
	@Test
	public void testImporteFacturaAntesDeJulio() {
		Date JUNE_6_2012 = Dates.fromString("15/6/2012");
		
		List<WorkOrder> averias = new ArrayList<WorkOrder>();
		averias.add( averia );
		Invoice factura = new Invoice( 0L, JUNE_6_2012, averias ); // iva 18%
		
		assertTrue( factura.getAmount() ==  295.0 );
	}

	/**
	 * Una avería al añadirla a una factura cambia su estado a FACTURADA al 
	 * añadirla por constructor
	 */
	@Test
	public void testAveriasFacturadas() {
		List<WorkOrder> averias = Arrays.asList( averia );
		new Invoice( 0L, averias );
		
		assertTrue( averia.getStatus() == WorkOrderStatus.INVOICED );
	}

	/**
	 * Una averia al añadirla a una factura cambia su estado a FACTURADA al 
	 * añadirla por asociación
	 */
	@Test
	public void testAveriasFacturadasAddAveria() {
		new Invoice( 0L ).addWorkOrder( averia );
		
		assertTrue( averia.getStatus() == WorkOrderStatus.INVOICED );
	}

	/**
	 * Varias averias al añadirlas a una factura cambian su estado a FACTURADA
	 */
	@Test
	public void testDosAveriasFacturadasAddAveria() {
		WorkOrder otraAveria = crearOtraAveria();
		
		Invoice f = new Invoice( 0L );
		f.addWorkOrder( averia );
		f.addWorkOrder( otraAveria );
		
		assertTrue( averia.getStatus() == WorkOrderStatus.INVOICED );
		assertTrue( otraAveria.getStatus() == WorkOrderStatus.INVOICED );
	}
	
	/**
	 * La fecha devuelta por getFecha() es una copia de la interna  
	 */
	@Test
	public void testGetFechaReturnsCopy() {
		Invoice f = new Invoice( 0L );
		Date one = f.getDate();
		Date another = f.getDate();
		
		assertTrue( one != another );
		assertTrue( one.equals( another ));
	}

	/**
	 * La fecha que se pasa en el constructor se copia para ser interna  
	 */
	@Test
	public void testContructorCopiesDate() {
		Date date = new Date();
		Date copy = new Date( date.getTime() );
		
		Invoice f = new Invoice( 0L, date );
		date.setTime( 0L ); // 1/1/1970 00:00
		Date gotten = f.getDate();
		
		assertTrue( gotten != date );
		assertTrue( ! gotten.equals( date ) );
		assertTrue( gotten.equals( copy ) );
	}

	/**
	 * La fecha que se pasa en setDate() se copia para ser interna  
	 */
	@Test
	public void testSetterCopiesDate() {
		Date now = new Date();
		Invoice f = new Invoice( 0L );
		
		f.setDate( now );
		now.setTime( 0L );
		Date date = f.getDate();
		
		assertTrue( ! now.equals( date ) );
	}

	/**
	 * Genera nueva factura esperando 100 milisegundos para evitar que coincida 
	 * el campo fecha si se generan dos facturas muy seguidas 
	 * (en el mismo milisegundo)
	 * 
	 * Puede dar problemas si la fecha forma parte de la identidad de la avería
	 * @return la avería creada
	 */
	private WorkOrder crearOtraAveria() {
		sleep( 100 );
		WorkOrder averia = new WorkOrder(vehiculo, "falla la junta la trocla otra vez");
		averia.assignTo( mecanico );
		
		Intervention i = new Intervention(mecanico, averia, 45);
		new Substitution(repuesto, i, 1);
		
		averia.markAsFinished();
		
		// importe = 100 repuesto + 37.5 mano de obra
		return averia;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
			// dont't care if this occurs
		}
	}

}
