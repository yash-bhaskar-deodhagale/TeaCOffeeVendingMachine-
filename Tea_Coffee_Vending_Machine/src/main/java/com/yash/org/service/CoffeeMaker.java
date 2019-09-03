package com.yash.org.service;

import static com.yash.org.constants.Constants.COFFEE;
import static com.yash.org.constants.Constants.COFFEE_CONSUMPTION;
import static com.yash.org.constants.Constants.COFFEE_MILK_CONSUMPTION;
import static com.yash.org.constants.Constants.COFFEE_MILK_WASTAGE;
import static com.yash.org.constants.Constants.COFFEE_PRICE;
import static com.yash.org.constants.Constants.COFFEE_READY;
import static com.yash.org.constants.Constants.COFFEE_SUGAR_CONSUMPTION;
import static com.yash.org.constants.Constants.COFFEE_SUGAR_WASTAGE;
import static com.yash.org.constants.Constants.COFFEE_WASTAGE;
import static com.yash.org.constants.Constants.COFFEE_WATER_CONSUMPTION;
import static com.yash.org.constants.Constants.COFFEE_WATER_WASTAGE;
import static com.yash.org.constants.Constants.MILK;
import static com.yash.org.constants.Constants.SUGAR;
import static com.yash.org.constants.Constants.WATER;

import java.util.function.IntFunction;
import java.util.function.Predicate;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;;

public class CoffeeMaker implements BeverageService {

	public String makeDrink(int quantity) {

		TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();

		IntFunction<Integer> coffeeRequired = coffeeQuantity -> coffeeQuantity * (COFFEE_CONSUMPTION + COFFEE_WASTAGE);
		IntFunction<Integer> milkRequired = coffeeQuantity -> coffeeQuantity
				* (COFFEE_MILK_CONSUMPTION + COFFEE_MILK_WASTAGE);
		IntFunction<Integer> sugarRequired = coffeeQuantity -> coffeeQuantity
				* (COFFEE_SUGAR_CONSUMPTION + COFFEE_SUGAR_WASTAGE);
		IntFunction<Integer> waterRequired = coffeeQuantity -> coffeeQuantity
				* (COFFEE_WATER_CONSUMPTION + COFFEE_WATER_WASTAGE);

		Predicate<Integer> isCoffeeEnough = coffeeAmount -> coffeeAmount > tCVendingMachine.getTeaContainer();
		Predicate<Integer> isMilkEnough = milkAmount -> milkAmount > tCVendingMachine.getMilkContainer();
		Predicate<Integer> isSugarEnough = sugarAmount -> sugarAmount > tCVendingMachine.getSugarContainer();
		Predicate<Integer> isWaterEnough = waterAmount -> waterAmount > tCVendingMachine.getWaterContainer();

		if (isWaterEnough.test(waterRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough water available to make coffee");
		if (isSugarEnough.test(sugarRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough sugar available to make coffee");
		if (isCoffeeEnough.test(coffeeRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough coffee available to make coffee");
		if (isMilkEnough.test(milkRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough milk available to make coffee");

		tCVendingMachine.setCoffeeContainer(tCVendingMachine.getCoffeeContainer() - coffeeRequired.apply(quantity));
		tCVendingMachine.setMilkContainer(tCVendingMachine.getMilkContainer() - milkRequired.apply(quantity));
		tCVendingMachine.setSugarContainer(tCVendingMachine.getSugarContainer() - sugarRequired.apply(quantity));
		tCVendingMachine.setWaterContainer(tCVendingMachine.getWaterContainer() - waterRequired.apply(quantity));

		tCVendingMachine.getTotalSaleMap().put(COFFEE, tCVendingMachine.getTotalSaleMap().get(COFFEE) + quantity);

		tCVendingMachine.getMaterialWasteMap().put(COFFEE,
				tCVendingMachine.getMaterialWasteMap().get(COFFEE) + (quantity * COFFEE_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(MILK,
				tCVendingMachine.getMaterialWasteMap().get(MILK) + (quantity * COFFEE_MILK_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(SUGAR,
				tCVendingMachine.getMaterialWasteMap().get(SUGAR) + (quantity * COFFEE_SUGAR_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(WATER,
				tCVendingMachine.getMaterialWasteMap().get(WATER) + (quantity * COFFEE_WATER_WASTAGE));

		String coffeeReadyMessage = COFFEE_READY + ".Please pay Rs" + quantity * COFFEE_PRICE + ".Thank you.";
		return coffeeReadyMessage;

	}

}
