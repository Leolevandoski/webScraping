package com.leo.core;

import java.io.IOException;
import java.util.Scanner;

import americanasTest.Americanas;

public class ProgramExe {

	private static Americanas americanas;
	
	static String product;

	public static void main(String[] args) throws IOException, InterruptedException {

		americanas = new Americanas();
		Scanner ler = new Scanner(System.in);

		System.out.println("Digite o Produto para pesquisa");

		product = ler.nextLine();

		ler.close();

		americanas.test();

	}

	public static String produto() {
		return product;

	}

}
