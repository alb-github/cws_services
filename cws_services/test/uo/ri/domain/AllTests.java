package uo.ri.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	WorkOrderTests.class, 
	InvoiceTests.class, 
	IntervencionTest.class, 
	ChargeTests.class,
	SustitutionTests.class 
})

public class AllTests {

}
