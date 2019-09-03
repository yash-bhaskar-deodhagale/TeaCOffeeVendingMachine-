package com.yash.org.service;

import static com.yash.org.constants.Constants.BLACKTEA;
import static com.yash.org.constants.Constants.BLACKTEA_CONSUMPTION;
import static com.yash.org.constants.Constants.BLACKTEA_PRICE;
import static com.yash.org.constants.Constants.BLACKTEA_READY;
import static com.yash.org.constants.Constants.BLACKTEA_SUGAR_CONSUMPTION;
import static com.yash.org.constants.Constants.BLACKTEA_SUGAR_WASTAGE;
import static com.yash.org.constants.Constants.BLACKTEA_WASTAGE;
import static com.yash.org.constants.Constants.BLACKTEA_WATER_CONSUMPTION;
import static com.yash.org.constants.Constants.BLACKTEA_WATER_WASTAGE;
import static com.yash.org.constants.Constants.SUGAR;
import static com.yash.org.constants.Constants.TEA;
import static com.yash.org.constants.Constants.WATER;

import java.util.function.IntFunction;
import java.util.function.Predicate;

import com.yash.org.exception.NotEnoughMaterialException;
import com.yash.org.tcvmsimulator.TeaCoffeeVendingMachineSimulator;;

public class BlackTeaMaker implements BeverageService {

	public String makeDrink(int quantity) {

		TeaCoffeeVendingMachineSimulator tCVendingMachine = TeaCoffeeVendingMachineSimulator.getInstance();

		IntFunction<Integer> teaRequired = bTeaQuantity -> bTeaQuantity * (BLACKTEA_CONSUMPTION + BLACKTEA_WASTAGE);
		IntFunction<Integer> sugarRequired = bTeaQuantity -> bTeaQuantity
				* (BLACKTEA_SUGAR_CONSUMPTION + BLACKTEA_SUGAR_WASTAGE);
		IntFunction<Integer> waterRequired = bTeaQuantity -> bTeaQuantity
				* (BLACKTEA_WATER_CONSUMPTION + BLACKTEA_WATER_WASTAGE);

		Predicate<Integer> isTeaEnough = teaAmount -> teaAmount > tCVendingMachine.getTeaContainer();
		Predicate<Integer> isSugarEnough = sugarAmount -> sugarAmount > tCVendingMachine.getSugarContainer();
		Predicate<Integer> isWaterEnough = waterAmount -> waterAmount > tCVendingMachine.getWaterContainer();

		if (isTeaEnough.test(teaRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough tea available to make blackTea");
		if (isSugarEnough.test(sugarRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough sugar available to make blackTea");
		if (isWaterEnough.test(waterRequired.apply(quantity)))
			throw new NotEnoughMaterialException("Not enough water available to make blackTea");

		tCVendingMachine.setTeaContainer(tCVendingMachine.getTeaContainer() - teaRequired.apply(quantity));
		tCVendingMachine.setSugarContainer(tCVendingMachine.getSugarContainer() - sugarRequired.apply(quantity));
		tCVendingMachine.setWaterContainer(tCVendingMachine.getWaterContainer() - waterRequired.apply(quantity));

		tCVendingMachine.getTotalSaleMap().put(BLACKTEA, tCVendingMachine.getTotalSaleMap().get(TEA) + quantity);
		tCVendingMachine.getMaterialWasteMap().put(TEA,
				tCVendingMachine.getMaterialWasteMap().get(TEA) + (quantity * BLACKTEA_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(SUGAR,
				tCVendingMachine.getMaterialWasteMap().get(SUGAR) + (quantity * BLACKTEA_SUGAR_WASTAGE));
		tCVendingMachine.getMaterialWasteMap().put(WATER,
				tCVendingMachine.getMaterialWasteMap().get(WATER) + (quantity * BLACKTEA_WATER_WASTAGE));

		String bTeaReadyMessage = BLACKTEA_READY + ".Please pay Rs" + quantity * BLACKTEA_PRICE + ".Thank you.";
		return bTeaReadyMessage;
	}

}
