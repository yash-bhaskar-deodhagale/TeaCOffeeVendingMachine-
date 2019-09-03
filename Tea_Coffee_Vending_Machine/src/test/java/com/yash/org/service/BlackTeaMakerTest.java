package com.yash.org.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.service.BlackTeaMaker;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;

public class BlackTeaMakerTest {
	
	BlackTeaMaker blackTea = new BlackTeaMaker();
	
	private TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();
	
	@Rule
	public ExpectedException execption = ExpectedException.none();

	@Before()
	public void setUp(){
		tCVendingMachine.initialise();
	}
	
	@Test()
	public void shouldReturnBlackTeaPreParedMessageWithGivenQuantity() {
		BlackTeaMaker blackTea = new BlackTeaMaker();
		assertEquals("Black Tea is Ready.Please pay Rs50.Thank you.",blackTea.makeDrink(10));
	}
	
	@Test()
	public void shouldReturnNotEnoughSugarExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough sugar available to make blackTea");
		blackTea.makeDrink(471);
	}

	@Test()
	public void shouldReturnNotEnoughTeaExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough tea available to make blackTea");
		blackTea.makeDrink(667);
	}

	@Test()
	public void shouldReturnNotEnoughWaterExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough water available to make blackTea");
		blackTea.makeDrink(134);
	}

}
