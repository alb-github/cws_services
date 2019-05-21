package uo.ri.associations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	AssignTests.class, 
	OrderTests.class, 
	ClassifyTests.class,
	InvoiceTests.class, 
	IntervenirTest.class, 
	PayTests.class,
	OnwTests.class, 
	SustituteTests.class 
})
public class AllTests {

}
