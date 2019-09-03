package com.yash.org.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.service.BlackCoffeeMaker;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;

public class BlackCoffeeMakerTest {
	
	BlackCoffeeMaker blackCoffee = new BlackCoffeeMaker();
	private TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();
	
	@Rule
	public ExpectedException execption = ExpectedException.none();
     
	@Before()
	public void setUp(){
		tCVendingMachine.initialise();
	}
	
	@Test()
	public void shouldReturnBlackCoffeePreParedMessageWithGivenQuantity() {
		assertEquals("Black Coffee is Ready.Please pay Rs120.Thank you.",blackCoffee.makeDrink(12));
	}
	
	@Test()
	public void shouldReturnNotEnoughSugarExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough sugar available to make blackCoffee");
		blackCoffee.makeDrink(471);
	}

	@Test()
	public void shouldReturnNotEnoughCoffeeExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough coffee available to make blackCoffee");
		blackCoffee.makeDrink(667);
	}

	@Test()
	public void shouldReturnNotEnoughWaterExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough water available to make blackCoffee");
		blackCoffee.makeDrink(134);
	}
}
