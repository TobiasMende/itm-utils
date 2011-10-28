package de.uniluebeck.itm.tr.util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RateLimiterUnitTest {

	private RateLimiter rateLimiter;

	private int slotLength = 2;

	private TimeUnit timeUnit = TimeUnit.SECONDS;

	@Test
	public void checkIfAllPassedObjectsSuccessfullyApprovedInOneSlot() {
		rateLimiter = new RateLimiterImpl(10, slotLength, timeUnit);
		for (int i = 0; i < 10; i++) {
			assertTrue(rateLimiter.checkIfInSlotAndCount());
			assertTrue(rateLimiter.approvedCount() == i + 1);
			assertTrue(rateLimiter.dismissedCount() == 0);
		}
		assertTrue(rateLimiter.approvedCount() == 10);
		assertTrue(rateLimiter.dismissedCount() == 0);
	}

	@Test
	public void checkIfAllPassedObjectsDismissesInOneSlot() {
		rateLimiter = new RateLimiterImpl(0, slotLength, timeUnit);
		for (int i = 0; i < 10; i++) {
			assertFalse(rateLimiter.checkIfInSlotAndCount());
			assertTrue(rateLimiter.dismissedCount() == i + 1);
			assertTrue(rateLimiter.approvedCount() == 0);
		}
		assertTrue(rateLimiter.dismissedCount() == 10);
		assertTrue(rateLimiter.approvedCount() == 0);
	}

	@Test
	public void checkIfAllPassedObjectsSuccessfullyApprovedForTwoSlots() throws InterruptedException {
		rateLimiter = new RateLimiterImpl(10, slotLength, timeUnit);
		for (int i = 0; i < 10; i++) {
			assertTrue(rateLimiter.checkIfInSlotAndCount());
			assertTrue(rateLimiter.approvedCount() == i + 1);
			assertTrue(rateLimiter.dismissedCount() == 0);
		}
		assertFalse(rateLimiter.checkIfInSlotAndCount());
		assertTrue(rateLimiter.dismissedCount() == 1);
		//move on to next slot
		rateLimiter.nextSlot();
		for (int i = 0; i < 10; i++) {
			assertTrue(rateLimiter.checkIfInSlotAndCount());
			assertTrue(rateLimiter.approvedCount() == i + 1);
			assertTrue(rateLimiter.dismissedCount() == 0);
		}
	}

}