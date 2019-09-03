package com.yash.org.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.service.TeaMaker;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;

public class TeaMakerTest {

	TeaMaker tea = new TeaMaker();
	private TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();
	
	@Rule
	public ExpectedException execption = ExpectedException.none();

	@Before()
	public void setUp(){
		tCVendingMachine.initialise();
	}
	
	@Test()
	public void shouldReturnTeaPreParedMessageWithGivenQuantity() {
		assertEquals("Tea is Ready.Please pay Rs10.Thank you.", tea.makeDrink(1));
	}

	@Test()
	public void shouldReturnNotEnoughSugarExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough sugar available to make tea");
		tea.makeDrink(471);
	}

	@Test()
	public void shouldReturnNotEnoughTeaExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough tea available to make tea");
		tea.makeDrink(334);
	}

	@Test()
	public void shouldReturnNotEnoughWaterExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough water available to make tea");
		tea.makeDrink(231);
	}

	@Test()
	public void shouldReturnNotEnoughMilkExceptionMessageWithGivenQuantity() {
		execption.expect(NotEnoughMaterialException.class);
		execption.expectMessage("Not enough milk available to make tea");
		tea.makeDrink(228);
	}

}
