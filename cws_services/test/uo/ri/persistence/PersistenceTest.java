package uo.ri.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.workshop.domain.Address;
import uo.ri.workshop.domain.Associations;
import uo.ri.workshop.domain.Cash;
import uo.ri.workshop.domain.Charge;
import uo.ri.workshop.domain.Client;
import uo.ri.workshop.domain.CreditCard;
import uo.ri.workshop.domain.Intervention;
import uo.ri.workshop.domain.Invoice;
import uo.ri.workshop.domain.Mechanic;
import uo.ri.workshop.domain.PaymentMean;
import uo.ri.workshop.domain.SparePart;
import uo.ri.workshop.domain.Substitution;
import uo.ri.workshop.domain.Vehicle;
import uo.ri.workshop.domain.VehicleType;
import uo.ri.workshop.domain.Voucher;
import uo.ri.workshop.domain.WorkOrder;


public class PersistenceTest {

	private EntityManagerFactory factory;
	private Client cliente;
	private Substitution sustitucion;
	private Charge cargo;

	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("carworkshop");
		List<Object> graph = createGraph();
		persistGraph(graph);
	}

	@After
	public void tearDown() {
		removeGraph();
		factory.close();
	}

	@Test
	public void testCliente() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Client cl = mapper.merge( cliente );
		
		assertNotNull( cl.getId() );
		assertEquals( cl.getSurname(), "apellidos");
		assertEquals( cl.getName(), "nombre");
		assertEquals( cl.getDni(), "dni");
		
		trx.commit();
		mapper.close();	
	}

	@Test
	public void testVehiculos() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Client cl = mapper.merge(cliente);
		Set<Vehicle> vehiculos = cl.getVehicles();
		Vehicle v = vehiculos.iterator().next();
		
		assertTrue( vehiculos.size() == 1 );
		assertSame( v.getClient(), cl);
		assertNotNull( v.getId());
		assertEquals( v.getMark(), "seat" );
		assertEquals( v.getModel(), "ibiza" );
		assertEquals( v.getPlateNumber(), "1234 GJI" );
		
		trx.commit();
		mapper.close();
	}

	@Test
	public void testSustituir() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Substitution s = mapper.merge( sustitucion );
		SparePart r = s.getSparePart();
		Intervention i = s.getIntervention();
		
		assertTrue( r.getSustituciones().contains(s) ); 
		assertTrue( i.getSustitutions().contains(s) );

		trx.commit();
		mapper.close();		
	}
		
	@Test
	public void testTrabajarArreglar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Substitution s = mapper.merge( sustitucion );
		Intervention i = s.getIntervention();
		Mechanic m = i.getMechanic();
		WorkOrder a = i.getWorkOrder();
		
		assertTrue( m.getInterventions().contains(i) ); 
		assertTrue( a.getInterventions().contains(i) ); 
		
		trx.commit();
		mapper.close();		
	}
	
	@Test
	public void testTener() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Substitution s = mapper.merge( sustitucion );
		WorkOrder a = s.getIntervention().getWorkOrder();
		Vehicle v = a.getVehicle();
		
		assertTrue( v.getWorkOrders().contains(a) ); 
		
		trx.commit();
		mapper.close();		
	}

	@Test
	public void testSerPoseer() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Substitution s = mapper.merge( sustitucion );
		Vehicle v = s.getIntervention().getWorkOrder().getVehicle();
		VehicleType tv = v.getVehicleType();
		Client c = v.getClient();
		
		assertTrue( tv.getVehicles().contains(v) ); 
		assertTrue( c.getVehicles().contains(v) ); 
		
		trx.commit();
		mapper.close();		
	}

	@Test
	public void testCargar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Charge c = mapper.merge( cargo );
		Invoice f = c.getInvoice();
		PaymentMean mp = c.getPaymentMean();
		
		assertTrue( mp.getCharges().contains(c) );
		assertTrue( f.getCharges().contains(c) );
		
		trx.commit();
		mapper.close();		
	}

	@Test
	public void testFacturar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Substitution s = mapper.merge( sustitucion );
		WorkOrder a = s.getIntervention().getWorkOrder();
		Invoice f = a.getInvoice();
		
		assertTrue( f.getWorkOrders().contains(a) );
		
		trx.commit();
		mapper.close();		
	}

	@Test
	public void testPagar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Substitution s = mapper.merge( sustitucion );
		Client c = s.getIntervention().getWorkOrder().getVehicle().getClient();
		Set<PaymentMean> medios = c.getPaymentMeans();

		for(PaymentMean mp: medios) {
			assertSame( mp.getClient(), c );
		}
		
		trx.commit();
		mapper.close();		
	}

	protected List<Object> createGraph() {

		cliente = new Client("dni", "nombre", "apellidos");
		Address address = new Address("street", "city", "zipcode");
		cliente.setAddress(address);
		Vehicle vehiculo = new Vehicle("1234 GJI", "seat", "ibiza");
		Associations.Own.link(cliente, vehiculo);
		
		VehicleType tipoVehiculo = new VehicleType("coche", 50.0);
		Associations.Classify.link(tipoVehiculo, vehiculo);
		
		WorkOrder averia = new WorkOrder(vehiculo, "falla la junta la trocla");
		Mechanic mecanico = new Mechanic("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);
	
		Intervention intervencion = new Intervention(mecanico, averia, 60);
		averia.markAsFinished();
		
		SparePart repuesto = new SparePart("R1001", "junta la trocla", 100.0);
		sustitucion = new Substitution(repuesto, intervencion, 2);
		
		Voucher bono = new Voucher("B-100", 100.0);
		bono.setDescripcion( "Voucher just for testing" );
		Associations.Pay.link(bono, cliente);
		
		CreditCard tarjetaCredito = new CreditCard( 
					"1234567", 
					"visa", 
					Dates.inYearsTime( 1 ) 
				);
		Associations.Pay.link(tarjetaCredito, cliente);
		
		Cash metalico = new Cash( cliente );
		
		Invoice factura = new Invoice( 1L );
		factura.setDate( Dates.today() );
		factura.addWorkOrder(averia);

		cargo = new Charge(factura, tarjetaCredito, factura.getAmount());
		
		List<Object> res = new LinkedList<Object>();
		
		res.add(tipoVehiculo);
		res.add(repuesto);
		res.add(mecanico);
		res.add(cliente);
		res.add(bono);
		res.add(tarjetaCredito);
		res.add(metalico);
		res.add(vehiculo);
		res.add(factura);
		res.add(averia);
		res.add(intervencion);
		res.add(sustitucion);
		res.add(cargo);
		
		return res;
	}
	
	private void persistGraph(List<Object> graph) {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		for(Object o: graph) {
			mapper.persist(o);
		}

		trx.commit();
		mapper.close();
	}
	
	private void removeGraph() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		List<Object> merged = mergeGraph(mapper);
		
		for(Object o: merged) {
			mapper.remove(o);
		}

		trx.commit();
		mapper.close();
	}

	private List<Object> mergeGraph(EntityManager mapper) {
		List<Object> res = new LinkedList<Object>();
		
		res.add( mapper.merge(cargo) );

		Substitution s  = mapper.merge( sustitucion );
		res.add( s );
		res.add( s.getSparePart() );
		res.add( s.getIntervention() );
		res.add( s.getIntervention().getMechanic() );
		res.add( s.getIntervention().getWorkOrder() );
		res.add( s.getIntervention().getWorkOrder().getVehicle() );
		res.add( s.getIntervention().getWorkOrder().getVehicle().getVehicleType() );
		res.add( s.getIntervention().getWorkOrder().getInvoice() );
		
		Client cl = mapper.merge(cliente);
		for(PaymentMean mp: cl.getPaymentMeans()) {
			res.add( mp );
		}
		res.add( cl );
		
		return res;
	}

}
