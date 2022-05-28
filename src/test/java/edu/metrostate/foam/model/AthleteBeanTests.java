package edu.metrostate.foam.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static edu.metrostate.ics425.foam.Athlete.*;

/**
 * @author skylar
 *
 */
class AthleteBeanTests {
	AthleteBean athlete;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		athlete = new AthleteBean();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		athlete = null;
	}

	/**
	 * Test method for {@link edu.metrostate.foam.model.AthleteBean#getAge()}.
	 * 
	 * Tests the day before the start date, the day of the start date, and the 
	 * day after the start date.
	 */
	@Test
	void testGetAge() {
		athlete.setDateOfBirth(OLYMPIC_START_DATE.minusDays(1));
		assertEquals(0, athlete.getAge());
		
		athlete.setDateOfBirth(OLYMPIC_START_DATE);
		assertEquals(0, athlete.getAge());
		
		athlete.setDateOfBirth(OLYMPIC_START_DATE.plusDays(1));
		assertNull(athlete.getAge());
	}

	/**
	 * Test method for {@link edu.metrostate.foam.model.AthleteBean#isEligible()}.
	 * 
	 * Tests the day before eligibility, the day of eligibility, and the day after 
	 * eligibility.
	 */
	@Test
	void testIsEligible() {
		athlete.setDateOfBirth(OLYMPIC_START_DATE.minusYears(OLYMPIC_ELIGIBLE_AGE).plusDays(1));
		assertFalse(athlete.isEligible());
		
		athlete.setDateOfBirth(OLYMPIC_START_DATE.minusYears(OLYMPIC_ELIGIBLE_AGE));
		assertTrue(athlete.isEligible());
		
		athlete.setDateOfBirth(OLYMPIC_START_DATE.minusYears(OLYMPIC_ELIGIBLE_AGE).minusDays(1));
		assertTrue(athlete.isEligible());
	}

}
