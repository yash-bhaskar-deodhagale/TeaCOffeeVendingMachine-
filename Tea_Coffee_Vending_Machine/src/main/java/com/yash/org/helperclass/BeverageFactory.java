package com.yash.org.helperclass;

import com.yash.org.service.BeverageService;
import com.yash.org.service.BlackCoffeeMaker;
import com.yash.org.service.BlackTeaMaker;
import com.yash.org.service.CoffeeMaker;
import com.yash.org.service.TeaMaker;

public class BeverageFactory {
	
	
	
	public BeverageFactory() {
		
	}

	public BeverageService getBeverage(int choice) {
		if (choice == 1) {
			return new TeaMaker();
		}
		if (choice == 2) {
			return new CoffeeMaker();
		}
		if (choice == 3) {
			return new BlackTeaMaker();
		}
		if (choice == 4) {
			return new BlackCoffeeMaker();
		}
		return null;
	}

}
