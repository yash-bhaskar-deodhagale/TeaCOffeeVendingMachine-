package com.yash.org.tcvmsimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.yash.org.helperclass.BeverageFactory;
import com.yash.org.helperclass.UserInput;
import com.yash.org.service.BeverageService;
import com.yash.org.service.BlackCoffeeMaker;
import com.yash.org.service.BlackTeaMaker;
import com.yash.org.service.CoffeeMaker;
import com.yash.org.service.TeaMaker;


@RunWith(MockitoJUnitRunner.class)
public class TeaCoffeeVendingMachineSimulatorTest {

	@InjectMocks
	TeaCoffeeVendingMachineSimulator tCVendingMachine;
	
	@Mock
	private Appender appenderMock;
	
	@Mock
	private UserInput userInputMock;
	
	
	@Mock
	BeverageFactory beverageFactoryMock;
	
	@Mock
	BeverageService beverageMock;
	
	@Mock
	TeaMaker teaMakerMock;
	
	@Mock
	BlackTeaMaker blackTeaMakerMock;
	
	@Mock
	CoffeeMaker coffeeMakerMock;
	
	@Mock
	BlackCoffeeMaker blackCoffeeMock;
	
	@Before
	public void setAppender() {
		appenderMock = mock(Appender.class);
		Logger.getRootLogger().addAppender(appenderMock);
	}

	@After
	public void removeAppender() {
		Logger.getRootLogger().removeAppender(appenderMock);
	}
	@Before
	public void setUp(){
		tCVendingMachine.initialise();
	}
	
	@Test
	public void ShouldReturnTeaReadyWhenUserSelectsOne(){
		when(userInputMock.getInput()).thenReturn(1,3,5);
		when(beverageFactoryMock.getBeverage(1)).thenReturn(teaMakerMock);
		beverageFactoryMock.getBeverage(1);
		when(beverageMock.makeDrink(3)).thenReturn("Tea is Ready.Please pay Rs30.Thank you.");
		beverageMock.makeDrink(3);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(3)).getInput();
		verify(beverageFactoryMock,times(1)).getBeverage(1);
		verify(beverageMock,times(1)).makeDrink(3);
		
		
	}
	
	@Test
	public void ShouldReturnCoffeeReadyWhenUserSelectsTwo(){
		when(userInputMock.getInput()).thenReturn(2,2,5);
		when(beverageFactoryMock.getBeverage(2)).thenReturn(coffeeMakerMock);
		beverageFactoryMock.getBeverage(2);
		when(beverageMock.makeDrink(2)).thenReturn("Coffee is Ready.Please pay Rs30.Thank you.");
		beverageMock.makeDrink(2);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(3)).getInput();
		verify(beverageFactoryMock,times(1)).getBeverage(2);
		verify(beverageMock,times(1)).makeDrink(2);
		
		
	}
	
	@Test
	public void ShouldReturnCoffeeReadyWhenUserSelectsThree(){
		when(userInputMock.getInput()).thenReturn(3,2,5);
		when(beverageFactoryMock.getBeverage(3)).thenReturn(blackTeaMakerMock);
		beverageFactoryMock.getBeverage(3);
		when(beverageMock.makeDrink(2)).thenReturn("BlackTea is Ready.Please pay Rs10.Thank you.");
		beverageMock.makeDrink(2);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(3)).getInput();
		verify(beverageFactoryMock,times(1)).getBeverage(3);
		verify(beverageMock,times(1)).makeDrink(2);
		
		
	}
	
	@Test
	public void ShouldReturnCoffeeReadyWhenUserSelectsFour(){
		when(userInputMock.getInput()).thenReturn(4,2,5);
		when(beverageFactoryMock.getBeverage(4)).thenReturn(blackCoffeeMock);
		beverageFactoryMock.getBeverage(4);
		when(beverageMock.makeDrink(2)).thenReturn("BlackCoffee is Ready.Please pay Rs20.Thank you.");
		beverageMock.makeDrink(2);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(3)).getInput();
		verify(beverageFactoryMock,times(1)).getBeverage(4);
		verify(beverageMock,times(1)).makeDrink(2);
		
		
	}
	@Test
	public void ShouldShowExceptionMessagesWhenUserInputsQuantityMoreThanAvailable(){
		when(userInputMock.getInput()).thenReturn(1,500,5);
		when(beverageFactoryMock.getBeverage(1)).thenReturn(blackCoffeeMock);
		beverageFactoryMock.getBeverage(1);
		when(beverageMock.makeDrink(500)).thenReturn("Not enough sugar available to make tea");
		beverageMock.makeDrink(500);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(3)).getInput();
		verify(beverageFactoryMock,times(1)).getBeverage(1);
		verify(beverageMock,times(1)).makeDrink(500);
		
		
	}
	
	@Test
	public void ShouldExitFromBeverageSelectionReadyWhenUserSelectsFive(){
		when(userInputMock.getInput()).thenReturn(5);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(1)).getInput();		
	}
	
	@Test
	public void ShouldGiveInValidInputMessageWhenUserSelectsInvalidInput(){
		when(userInputMock.getInput()).thenReturn(6,5);
		tCVendingMachine.beverageMaker();
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldReturnTrueIfUserSelectOne(){
		when(userInputMock.getInput()).thenReturn(1);
		assertTrue(tCVendingMachine.doYouWantToContinue());
		verify(userInputMock,times(1)).getInput();		
	}
	
	@Test
	public void ShouldReturnFalseIfUserSelectTwo(){
		when(userInputMock.getInput()).thenReturn(2);
		assertFalse(tCVendingMachine.doYouWantToContinue());
		verify(userInputMock,times(1)).getInput();		
	}
	
	@Test
	public void ShouldRefillTeaContainerIfUserSelectOne(){
		when(userInputMock.getInput()).thenReturn(1,2);
		tCVendingMachine.refillOption();
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldRefillCoffeeContainerIfUserSelectTwo(){
		when(userInputMock.getInput()).thenReturn(2,2);
		tCVendingMachine.refillOption();
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldRefillSugarContainerIfUserSelectThree(){
		when(userInputMock.getInput()).thenReturn(3,2);
		tCVendingMachine.refillOption();
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldRefillWaterContainerIfUserSelectFour(){
		when(userInputMock.getInput()).thenReturn(4,2);
		tCVendingMachine.refillOption();
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldRefillMilkContainerIfUserSelectFive(){
		when(userInputMock.getInput()).thenReturn(5,2);
		tCVendingMachine.refillOption();
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldExitRefillSelectionIfUserSelectSix(){
		when(userInputMock.getInput()).thenReturn(6);
		tCVendingMachine.refillOption();
		verify(userInputMock,times(1)).getInput();		
	}
	
	@Test
	public void ShouldGiveInvalidSelctionForRefillIfUserSelectInvalidInput(){
		when(userInputMock.getInput()).thenReturn(7,6);
		tCVendingMachine.refillOption();
		verify(userInputMock,times(2)).getInput();		
	}
	
	@Test
	public void ShouldCallBeverageMakerIfUserSelectOne(){
		when(userInputMock.getInput()).thenReturn(1,1,1,2,6);
		tCVendingMachine.teaCoffeeVendingMachine();
		verify(userInputMock,times(5)).getInput();		
	}
	
	@Test
	public void ShouldCallRefillIfUserSelectTwo(){
		when(userInputMock.getInput()).thenReturn(1,1,1,2,2,1,2,6);
		tCVendingMachine.teaCoffeeVendingMachine();
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		verify(userInputMock,times(8)).getInput();		
	}
	
	@Test
	public void ShouldCallResetIfUserSelectThree(){
		when(userInputMock.getInput()).thenReturn(1,1,10,2,3,6);
		tCVendingMachine.teaCoffeeVendingMachine();
		System.out.println("tCVendingMachine.getSugarContainer()"+tCVendingMachine.getSugarContainer());
		assertEquals(2000,tCVendingMachine.getTeaContainer());
		assertEquals(2000,tCVendingMachine.getCoffeeContainer());
		assertEquals(8000,tCVendingMachine.getSugarContainer());
		assertEquals(15000,tCVendingMachine.getWaterContainer());
		assertEquals(10000,tCVendingMachine.getMilkContainer());
		verify(userInputMock,times(6)).getInput();		
	}
	
	@Test
	public void ShouldShowContainerStatusIfUserSelectFour(){
		tCVendingMachine.setTeaContainer(1000);
		tCVendingMachine.setCoffeeContainer(800);
		tCVendingMachine.setSugarContainer(4300);
		tCVendingMachine.setWaterContainer(7800);
		tCVendingMachine.setMilkContainer(3500);
		tCVendingMachine.getMaterialWasteMap().put("tea",300);
		tCVendingMachine.getMaterialWasteMap().put("coffee",170);
		tCVendingMachine.getMaterialWasteMap().put("sugar",2000);
		tCVendingMachine.getMaterialWasteMap().put("water",1200);
		tCVendingMachine.getMaterialWasteMap().put("milk",900);
		tCVendingMachine.getRefillMap().put("tea",3);
		tCVendingMachine.getRefillMap().put("coffee",5);
		tCVendingMachine.getRefillMap().put("sugar",34);
		tCVendingMachine.getRefillMap().put("water",1);
		tCVendingMachine.getRefillMap().put("milk",9);
		when(userInputMock.getInput()).thenReturn(4,6);
		tCVendingMachine.teaCoffeeVendingMachine();
		verify(userInputMock,times(2)).getInput();
		verify(appenderMock, times(24)).doAppend((LoggingEvent) any());		
	}
	@Test
	public void ShouldShowTotalSalesStatusIfUserSelectFive(){
		tCVendingMachine.getTotalSaleMap().put("tea",3);
		tCVendingMachine.getTotalSaleMap().put("coffee",5);
		tCVendingMachine.getTotalSaleMap().put("blackCoffee",34);
		tCVendingMachine.getTotalSaleMap().put("blackTea",1);
		when(userInputMock.getInput()).thenReturn(5,6);
		tCVendingMachine.teaCoffeeVendingMachine();
		verify(userInputMock,times(2)).getInput();
		verify(appenderMock, times(26)).doAppend((LoggingEvent) any());		
	}
	@Test
	public void ShouldShowInvalidInputIfUserSelectInvalidInput(){
		when(userInputMock.getInput()).thenReturn(7,6);
		tCVendingMachine.teaCoffeeVendingMachine();
		verify(userInputMock,times(2)).getInput();	
	}
}
