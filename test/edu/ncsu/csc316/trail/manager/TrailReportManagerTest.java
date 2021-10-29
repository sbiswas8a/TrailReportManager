/**
 * 
 */
package edu.ncsu.csc316.trail.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.trail.factory.DSAFactory;

/** Test class for TrailReportManager and TrailManager
 * @author Sumit Biswas
 *
 */
public class TrailReportManagerTest {
	
	/** TrailReportManager instance used for testing */
	private TrailReportManager manager;

	/** Setup
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager = new TrailReportManager("input/sample2.csv");
	}
	
	/** Tests DSAFactory */
	@Test
	public void testFactory() {
		DSAFactory factory = new DSAFactory();
		assertNotNull(factory);
	}

	/**
	 * Test method for {@link edu.ncsu.csc316.trail.manager.TrailReportManager#getLeastCostlyTrailsReport(double)}.
	 */
	@Test
	public void testGetLeastCostlyTrailsReport() {
		String expected = "Minimum Trails for $9240.00 ($0.50 per linear foot) [\n" + 
				"   from Coffee Shop to Dog Park (2640 feet for $1320.00)\n" + 
				"   from Beautiful Falls to Coffee Shop (3168 feet for $1584.00)\n" + 
				"   from Beautiful Falls to Chicken Coop (3168 feet for $1584.00)\n" + 
				"   from Coffee Shop to Elephant Sculpture (3696 feet for $1848.00)\n" + 
				"   from Airlie Gardens to Beautiful Falls (5808 feet for $2904.00)\n" + 
				"]";
		String result = manager.getLeastCostlyTrailsReport(.50);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link edu.ncsu.csc316.trail.manager.TrailReportManager#getRestroomLocations(int)}.
	 */
	@Test
	public void testGetRestroomLocations() {
		String expected = "Attractions with adjacent trails longer than 5300 feet [\n   Airlie Gardens\n]";
		String result = manager.getRestroomLocations(5300);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link edu.ncsu.csc316.trail.manager.TrailReportManager#getTrailSignLocations()}.
	 */
	@Test
	public void testGetTrailSignLocations() {
		String expected = "Attractions that need a trail navigation sign [\n   Beautiful Falls: 3 intersecting trails\n   Coffee Shop: 3 intersecting trails\n]";
		String result = manager.getTrailSignLocations();
		assertEquals(expected, result);
	}

}
