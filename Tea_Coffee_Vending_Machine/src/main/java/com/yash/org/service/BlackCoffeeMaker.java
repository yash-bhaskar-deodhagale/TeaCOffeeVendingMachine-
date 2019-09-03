package com.yash.org.service;

import static com.yash.org.constants.Constants.BLACKCOFFEE;
import static com.yash.org.constants.Constants.BLACKCOFFEE_CONSUMPTION;
import static com.yash.org.constants.Constants.BLACKCOFFEE_PRICE;
import static com.yash.org.constants.Constants.BLACKCOFFEE_READY;
import static com.yash.org.constants.Constants.BLACKCOFFEE_SUGAR_CONSUMPTION;
import static com.yash.org.constants.Constants.BLACKCOFFEE_SUGAR_WASTAGE;
import static com.yash.org.constants.Constants.BLACKCOFFEE_WASTAGE;
import static com.yash.org.constants.Constants.BLACKCOFFEE_WATER_CONSUMPTION;
import static com.yash.org.constants.Constants.BLACKCOFFEE_WATER_WASTAGE;
import static com.yash.org.constants.Constants.COFFEE;
import static com.yash.org.constants.Constants.SUGAR;
import static com.yash.org.constants.Constants.WATER;

import java.util.function.IntFunction;
import java.util.function.Predicate;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;

public class BlackCoffeeMaker implements BeverageService {

	public String makeDrink(int quantity) {

		TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();

		IntFunction<Integer> coffeeRequired = bCoffeeQuantity -> bCoffeeQuantity
				* (BLACKCOFFEE_CONSUMPTION + BLACKCOFFEE_WASTAGE);
		IntFunction<Integer> sugarRequired = bCoffeeQuantity -> bCoffeeQuantity
				* (BLACKCOFFEE_SUGAR_CONSUMPTION + BLACKCOFFEE_SUGAR_WASTAGE);
		IntFunction<Integer> waterRequired = bCoffeeQuantity -> bCoffeeQuantity
				* (BLACKCOFFEE_WATER_CONSUMPTION + BLACKCOFFEE_WATER_WASTAGE);

		Predicate<Integer> isCoffeeEnough = coffeeAmount -> coffeeAmount > tCVendingMachine.getTeaContainer();
		Predicate<Integer> isSugarEnough = sugarAmount -> sugarAmount > tCVendingMachine.getSugarContainer();
		Predicate<Integer> isWaterEnough = waterAmount -> waterAmount > tCVendingMachine.getWaterContainer();

		if (isCoffeeEnough.test(coffeeRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough coffee available to make blackCoffee");
		if (isSugarEnough.test(sugarRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough sugar available to make blackCoffee");
		if (isWaterEnough.test(waterRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough water available to make blackCoffee");

		tCVendingMachine.setCoffeeContainer(tCVendingMachine.getCoffeeContainer() - coffeeRequired.apply(quantity));
		tCVendingMachine.setSugarContainer(tCVendingMachine.getSugarContainer() - sugarRequired.apply(quantity));
		tCVendingMachine.setWaterContainer(tCVendingMachine.getWaterContainer() - waterRequired.apply(quantity));

		tCVendingMachine.getTotalSaleMap().put(BLACKCOFFEE,
				tCVendingMachine.getTotalSaleMap().get(BLACKCOFFEE) + quantity);

		tCVendingMachine.getMaterialWasteMap().put(COFFEE,
				tCVendingMachine.getMaterialWasteMap().get(COFFEE) + (quantity * BLACKCOFFEE_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(SUGAR,
				tCVendingMachine.getMaterialWasteMap().get(SUGAR) + (quantity * BLACKCOFFEE_SUGAR_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(WATER,
				tCVendingMachine.getMaterialWasteMap().get(WATER) + (quantity * BLACKCOFFEE_WATER_WASTAGE));

		String bcoffeeReadyMessage = BLACKCOFFEE_READY + ".Please pay Rs" + quantity * BLACKCOFFEE_PRICE
				+ ".Thank you.";
		return bcoffeeReadyMessage;

	}

}
