package com.yash.org.tcvmsimulator;

import static com.yash.org.constants.Constants.BLACKCOFFEE;
import static com.yash.org.constants.Constants.BLACKCOFFEE_PRICE;
import static com.yash.org.constants.Constants.BLACKTEA;
import static com.yash.org.constants.Constants.BLACKTEA_PRICE;
import static com.yash.org.constants.Constants.COFFEE;
import static com.yash.org.constants.Constants.COFFEE_PRICE;
import static com.yash.org.constants.Constants.MILK;
import static com.yash.org.constants.Constants.SUGAR;
import static com.yash.org.constants.Constants.TEA;
import static com.yash.org.constants.Constants.TEA_PRICE;
import static com.yash.org.constants.Constants.WATER;
import static com.yash.org.constants.Constants.ZERO;
import static com.yash.org.constants.Constants.TEA_CONTAINER;
import static com.yash.org.constants.Constants.COFFEE_CONTAINER;
import static com.yash.org.constants.Constants.SUGAR_CONTAINER;
import static com.yash.org.constants.Constants.WATER_CONTAINER;
import static com.yash.org.constants.Constants.MILK_CONTAINER;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.helperclass.BeverageFactory;
import com.yash.org.helperclass.UserInput;
import com.yash.org.service.BeverageService;

public class TeaCoffeeVendingMachineSimulator {
	private static TeaCoffeeVendingMachineSimulator tcVendingMachine;
	private static final Logger LOGGER = Logger.getLogger(TeaCoffeeVendingMachineSimulator.class);
	private int teaContainer;
	private int coffeeContainer;
	private int sugarContainer;
	private int waterContainer;
	private int milkContainer;

	private UserInput userInput = new UserInput();
	private Map<String, Integer> totalSaleMap;
	private Map<String, Integer> materialWasteMap;
	private Map<String, Integer> refillMap;

	private TeaCoffeeVendingMachineSimulator() {

		totalSaleMap = new HashMap<String, Integer>();
		materialWasteMap = new HashMap<String, Integer>();
		refillMap = new HashMap<String, Integer>();
		initialise();
		setUpMap();
	}

	public static TeaCoffeeVendingMachineSimulator getInstance() {
		if (tcVendingMachine == null) {
			tcVendingMachine = new TeaCoffeeVendingMachineSimulator();
			return tcVendingMachine;
		}
		return tcVendingMachine;
	}

	public int getTeaContainer() {
		return teaContainer;
	}

	public void setTeaContainer(int teaContainer) {
		this.teaContainer = teaContainer;
	}

	public int getCoffeeContainer() {
		return coffeeContainer;
	}

	public void setCoffeeContainer(int coffeeContainer) {
		this.coffeeContainer = coffeeContainer;
	}

	public int getSugarContainer() {
		return sugarContainer;
	}

	public void setSugarContainer(int sugarContainer) {
		this.sugarContainer = sugarContainer;
	}

	public int getWaterContainer() {
		return waterContainer;
	}

	public void setWaterContainer(int waterContainer) {
		this.waterContainer = waterContainer;
	}

	public int getMilkContainer() {
		return milkContainer;
	}

	public void setMilkContainer(int milkContainer) {
		this.milkContainer = milkContainer;
	}

	public UserInput getUserInput() {
		return userInput;
	}

	public void setUserInput(UserInput userInput) {
		this.userInput = userInput;
	}

	public Map<String, Integer> getTotalSaleMap() {
		return totalSaleMap;
	}

	public Map<String, Integer> getMaterialWasteMap() {
		return materialWasteMap;
	}

	public Map<String, Integer> getRefillMap() {
		return refillMap;
	}

	public void setUpMap() {
		totalSaleMap.put(TEA, ZERO);
		totalSaleMap.put(BLACKTEA, ZERO);
		totalSaleMap.put(COFFEE, ZERO);
		totalSaleMap.put(BLACKCOFFEE, ZERO);
		materialWasteMap.put(TEA, ZERO);
		materialWasteMap.put(COFFEE, ZERO);
		materialWasteMap.put(MILK, ZERO);
		materialWasteMap.put(SUGAR, ZERO);
		materialWasteMap.put(WATER, ZERO);
		refillMap.put(TEA, ZERO);
		refillMap.put(COFFEE, ZERO);
		refillMap.put(MILK, ZERO);
		refillMap.put(SUGAR, ZERO);
		refillMap.put(WATER, ZERO);
	}

	public void initialise() {
		teaContainer = TEA_CONTAINER;
		coffeeContainer = COFFEE_CONTAINER;
		sugarContainer = SUGAR_CONTAINER;
		waterContainer = WATER_CONTAINER;
		milkContainer = MILK_CONTAINER;

	}

	public void refillContainer(int choice) {
		if (choice == 1) {
			setTeaContainer(2000);
			refillMap.put(TEA, refillMap.get(TEA) + 1);
			LOGGER.info("Tea Refilled");
		} else if (choice == 2) {
			setTeaContainer(2000);
			refillMap.put(COFFEE, refillMap.get(COFFEE) + 1);
			LOGGER.info("Coffee Refilled");
		} else if (choice == 3) {
			setTeaContainer(2000);
			refillMap.put(SUGAR, refillMap.get(SUGAR) + 1);
			LOGGER.info("Sugar Refilled");
		} else if (choice == 4) {
			setTeaContainer(2000);
			refillMap.put(WATER, refillMap.get(WATER) + 1);
			LOGGER.info("Water Refilled");
		} else if (choice == 5) {
			setTeaContainer(2000);
			refillMap.put(MILK, refillMap.get(MILK) + 1);
			LOGGER.info("Milk Refilled");
		}

	}

	public void totalSalesReport() {
		int totalQuantity = getTotalSaleMap().values().stream().mapToInt(x -> x).sum();
		int totalCost = (getTotalSaleMap().get(TEA) * TEA_PRICE) + getTotalSaleMap().get(BLACKTEA) * BLACKTEA_PRICE
				+ getTotalSaleMap().get(COFFEE) * COFFEE_PRICE + getTotalSaleMap().get(BLACKCOFFEE) * BLACKCOFFEE_PRICE;
		String header = String.format("%14s %14s %14s", "Beverage", "Quantity", "Amount");
		String teaRow = String.format("%14s %14s %14s", "TEA", getTotalSaleMap().get(TEA),
				getTotalSaleMap().get(TEA) * TEA_PRICE);
		String blackTeaRow = String.format("%14s %14s %14s", "BLACKTEA", getTotalSaleMap().get(BLACKTEA),
				getTotalSaleMap().get(BLACKTEA) * BLACKTEA_PRICE);
		String coffeeRow = String.format("%14s %14s %14s", "COFFEE", getTotalSaleMap().get(COFFEE),
				getTotalSaleMap().get(COFFEE) * COFFEE_PRICE);
		String blackCoffeeRow = String.format("%14s %14s %14s", "BLACKCOFFEE", getTotalSaleMap().get(BLACKCOFFEE),
				getTotalSaleMap().get(BLACKCOFFEE) * BLACKCOFFEE_PRICE);
		String total = String.format("%14s %14s %14s", "TOTAL", totalQuantity, totalCost);

		LOGGER.info(header);
		LOGGER.info("-----------------------------------------------------------");
		LOGGER.info(teaRow);
		LOGGER.info(blackTeaRow);
		LOGGER.info(coffeeRow);
		LOGGER.info(blackCoffeeRow);
		LOGGER.info("-----------------------------------------------------------");
		LOGGER.info(total);
		LOGGER.info("\n");

	}

	public void containerStatusReport() {
		String header = String.format("%14s %14s %14s %14s", "Material", "Remaining", "Wastage", "RefillCount");
		String teaRow = String.format("%14s %14s %14s %14s", "TEA", getTeaContainer(), getMaterialWasteMap().get(TEA),
				getRefillMap().get(TEA));
		String coffeeRow = String.format("%14s %14s %14s %14s", "COFFEE", getCoffeeContainer(),
				getMaterialWasteMap().get(COFFEE), getRefillMap().get(COFFEE));
		String sugarRow = String.format("%14s %14s %14s %14s", "SUGAR", getSugarContainer(),
				getMaterialWasteMap().get(SUGAR), getRefillMap().get(SUGAR));
		String waterRow = String.format("%14s %14s %14s %14s", "WATER", getWaterContainer(),
				getMaterialWasteMap().get(WATER), getRefillMap().get(WATER));
		String milkRow = String.format("%14s %14s %14s %14s", "MILK", getMilkContainer(),
				getMaterialWasteMap().get(MILK), getRefillMap().get(MILK));

		LOGGER.info(header);
		LOGGER.info("------------------------------------------------------------------------------");
		LOGGER.info(teaRow);
		LOGGER.info(coffeeRow);
		LOGGER.info(sugarRow);
		LOGGER.info(waterRow);
		LOGGER.info(milkRow);

	}

	public void teaCoffeeVendingMachine() {
		LOGGER.info("Welcome to Tea Coffee Vending Machine");
		boolean orderLoop = true;
		while (orderLoop) {
			LOGGER.info("Select from below option:");
			LOGGER.info("1.Order Beverage");
			LOGGER.info("2.Refill Container");
			LOGGER.info("3.Reset Conatiner");
			LOGGER.info("4.Container Status");
			LOGGER.info("5.Total sales");
			LOGGER.info("6.Exit");

			LOGGER.info("Enter your choice: ");
			int choice = userInput.getInput();

			switch (choice) {

			case 1:
				beverageMaker();
				break;
			case 2:
				refillOption();
				break;
			case 3:
				initialise();
				LOGGER.info("Container Reset Done");
				break;
			case 4:
				containerStatusReport();
				break;
			case 5:
				totalSalesReport();
				break;
			case 6:
				System.out.println("Exit");
				orderLoop = false;
				break;
			default:
				LOGGER.info("Enter valid input 1,2,3,4,5 or 6");
				LOGGER.info("\n");

			}
		}
	}

	public void beverageMaker() {
		boolean beverageLoop = true;
		BeverageService beverageService;
		while (beverageLoop) {

			LOGGER.info("1.Tea");
			LOGGER.info("2.Coffee");
			LOGGER.info("3.Black Tea");
			LOGGER.info("4.Black Coffee");
			LOGGER.info("5.Exit");
			LOGGER.info("Enter your choice: ");
			int beverageChoice = userInput.getInput();
			int beverageSwitch = 3;
			Predicate<Integer> checkOption = x -> x == 1 || x == 2 || x == 3 || x == 4;

			if (checkOption.test(beverageChoice)) {
				beverageSwitch = 1;
			} else if (beverageChoice == 5) {
				beverageSwitch = 2;
			}
			switch (beverageSwitch) {
			case 1:
				int quantity = getQuantityFromUser();
				BeverageFactory factory = new BeverageFactory();
				beverageService = factory.getBeverage(beverageChoice);
				try {
					String beverageMessage = beverageService.makeDrink(quantity);
					LOGGER.info(beverageMessage);
				} catch (NotEnoughMaterialException notEnoughMaterialexception) {
					String exceptionMessage = notEnoughMaterialexception.getMessage();
					LOGGER.error(exceptionMessage);
				}
				beverageLoop = doYouWantToContinue();
				break;
			case 2:
				beverageLoop = false;
				break;
			default:
				LOGGER.info("Enter valid input 1,2,3,4 or 5");
				break;
			}
		}

	}

	public void refillOption() {
		boolean refillLoop = true;
		while (refillLoop) {
			LOGGER.info("1.Tea(" + getTeaContainer() + ")");
			LOGGER.info("2.Coffee(" + getCoffeeContainer() + ")");
			LOGGER.info("3.Sugar(" + getSugarContainer() + ")");
			LOGGER.info("4.Water(" + getWaterContainer() + ")");
			LOGGER.info("5.Milk(" + getMilkContainer() + ")");
			LOGGER.info("6.Exit");
			LOGGER.info("Enter your choice: ");
			int refillChoice = userInput.getInput();
			int refillSwitch = 0;
			Predicate<Integer> checkOption = x -> x == 1 || x == 2 || x == 3 || x == 4 || x == 5;
			if (checkOption.test(refillChoice)) {
				refillSwitch = 1;
			} else if (refillChoice == 6) {
				refillSwitch = 2;
			}

			switch (refillSwitch) {
			case 1:
				refillContainer(refillChoice);
				refillLoop = doYouWantToContinue();
				break;
			case 2:
				refillLoop = false;
				break;
			default:
				LOGGER.info(" Note :Enter valid input 1,2,3,4,5 or 6");
			}
		}

	}

	public int getQuantityFromUser() {
		LOGGER.info("How many do you want: ");
		return userInput.getInput();
	}

	public boolean doYouWantToContinue() {
		LOGGER.info("Do you want to continue? ");
		LOGGER.info("1.Yes");
		LOGGER.info("2.No");
		LOGGER.info("Enter your choice");
		boolean toContinue = false;
		int choice = userInput.getInput();
		if (choice == 1) {
			toContinue = true;
		}
		return toContinue;
	}

}
