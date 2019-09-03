package com.yash.org.helperclass;

import java.util.Scanner;

public class UserInput {

	private static Scanner scanner=new Scanner(System.in);
	
	public Integer getInput(){
		return scanner.nextInt();
	}
}
