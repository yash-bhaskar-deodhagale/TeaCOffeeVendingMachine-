package com.yash.org.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.service.CoffeeMaker;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;

public class CoffeeMakerTest {
	
	CoffeeMaker coffee = new CoffeeMaker();
	
	private TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();
	
	@Rule
	public ExpectedException execption = ExpectedException.none();
	
	@Before()
	public void setUp(){
		tCVendingMachine.initialise();
	}
	
	@Test()
	public void shouldReturnCoffeePreParedMessageWithGivenQuantity() {
		assertEquals("Coffee is Ready.Please pay Rs225.Thank you.",coffee.makeDrink(15));
	}
	
	@Test()
	public void shouldReturnNotEnoughSugarExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough sugar available to make coffee");
		coffee.makeDrink(471);
	}

	@Test()
	public void shouldReturnNotEnoughMilkExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough milk available to make coffee");
		coffee.makeDrink(114);
	}

	@Test()
	public void shouldReturnNotEnoughWaterExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough water available to make coffee");
		coffee.makeDrink(653);
	}
	
	@Test()
	public void shouldReturnNotEnoughCoffeeExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough coffee available to make coffee");
		coffee.makeDrink(401);
	}
}
