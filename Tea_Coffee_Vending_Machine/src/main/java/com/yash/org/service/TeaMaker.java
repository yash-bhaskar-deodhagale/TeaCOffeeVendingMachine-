package com.yash.org.service;

import static com.yash.org.constants.Constants.MILK;
import static com.yash.org.constants.Constants.SUGAR;
import static com.yash.org.constants.Constants.TEA;
import static com.yash.org.constants.Constants.TEA_CONSUMPTION;
import static com.yash.org.constants.Constants.TEA_MILK_CONSUMPTION;
import static com.yash.org.constants.Constants.TEA_MILK_WASTAGE;
import static com.yash.org.constants.Constants.TEA_PRICE;
import static com.yash.org.constants.Constants.TEA_READY;
import static com.yash.org.constants.Constants.TEA_SUGAR_CONSUMPTION;
import static com.yash.org.constants.Constants.TEA_SUGAR_WASTAGE;
import static com.yash.org.constants.Constants.TEA_WASTAGE;
import static com.yash.org.constants.Constants.TEA_WATER_CONSUMPTION;
import static com.yash.org.constants.Constants.TEA_WATER_WASTAGE;
import static com.yash.org.constants.Constants.WATER;

import java.util.function.IntFunction;
import java.util.function.Predicate;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;

public class TeaMaker implements BeverageService {

	public String makeDrink(int quantity) {

		TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();

		IntFunction<Integer> teaRequired = teaQuantity -> teaQuantity * (TEA_CONSUMPTION + TEA_WASTAGE);
		IntFunction<Integer> milkRequired = teaQuantity -> teaQuantity * (TEA_MILK_CONSUMPTION + TEA_MILK_WASTAGE);
		IntFunction<Integer> sugarRequired = teaQuantity -> teaQuantity * (TEA_SUGAR_CONSUMPTION + TEA_SUGAR_WASTAGE);
		IntFunction<Integer> waterRequired = teaQuantity -> teaQuantity * (TEA_WATER_CONSUMPTION + TEA_WATER_WASTAGE);

		Predicate<Integer> isTeaEnough = teaAmount -> teaAmount > tCVendingMachine.getTeaContainer();
		Predicate<Integer> isMilkEnough = milkAmount -> milkAmount > tCVendingMachine.getMilkContainer();
		Predicate<Integer> isSugarEnough = sugarAmount -> sugarAmount > tCVendingMachine.getSugarContainer();
		Predicate<Integer> isWaterEnough = waterAmount -> waterAmount > tCVendingMachine.getWaterContainer();

		if (isSugarEnough.test(sugarRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough sugar available to make tea");
		if (isTeaEnough.test(teaRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough tea available to make tea");
		if (isWaterEnough.test(waterRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough water available to make tea");
		if (isMilkEnough.test(milkRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough milk available to make tea");

		tCVendingMachine.setTeaContainer(tCVendingMachine.getTeaContainer() - teaRequired.apply(quantity));
		tCVendingMachine.setMilkContainer(tCVendingMachine.getMilkContainer() - milkRequired.apply(quantity));
		tCVendingMachine.setSugarContainer(tCVendingMachine.getSugarContainer() - sugarRequired.apply(quantity));
		tCVendingMachine.setWaterContainer(tCVendingMachine.getWaterContainer() - waterRequired.apply(quantity));

		tCVendingMachine.getTotalSaleMap().put(TEA, tCVendingMachine.getTotalSaleMap().get(TEA) + quantity);

		tCVendingMachine.getMaterialWasteMap().put(TEA,
				tCVendingMachine.getMaterialWasteMap().get(TEA) + (quantity * TEA_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(MILK,
				tCVendingMachine.getMaterialWasteMap().get(MILK) + (quantity * TEA_MILK_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(SUGAR,
				tCVendingMachine.getMaterialWasteMap().get(SUGAR) + (quantity * TEA_SUGAR_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(WATER,
				tCVendingMachine.getMaterialWasteMap().get(WATER) + (quantity * TEA_WATER_WASTAGE));

		String teaReadyMessage = TEA_READY + ".Please pay Rs" + quantity * TEA_PRICE + ".Thank you.";
		return teaReadyMessage;
	}

}
