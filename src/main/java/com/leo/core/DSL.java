package com.leo.core;

import static com.leo.core.DriverFactory.getDriver;
import static com.leo.core.ProgramExe.produto;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;


import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DSL {

	private static BufferedWriter buffered;
	

	public void bufferedConfig() throws IOException {
		
		String product = "s";
//		String product = produto();
		String path = "/eclipse/Americanas " + product + ".csv";

		buffered = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.ISO_8859_1));
	}

	public void tabela() throws IOException {

		buffered.append("PRODUTO");
		buffered.append(';');
		buffered.append("PREÇO");
		buffered.append(';');
		buffered.append("DESCONTO");
		buffered.append(';');
		buffered.append("VENDEDOR");
		buffered.newLine();

	}
	
	public void pulaColuna() throws IOException {
		buffered.append(';');
	}

	public void pulaLinha() throws IOException {
		buffered.newLine();
	}

	public void escreve(String valor) throws IOException {
		buffered.append(valor);
	}

	public void flush() throws IOException {
		buffered.flush();
	}

	public void fecharBuffered() throws IOException {
		buffered.close();
	}

	public void escrevePulaColuna(String valor) throws IOException {

		buffered.append(valor);
		buffered.append(';');

	}
	
	public String quantidadeDoProduto(By by) {
		
		return getDriver().findElement(by).getText();
	}
	
	public String nomeDoProduto(By by) {
		return getDriver().findElement(by).getText();
	}

	public String precoDoProduto(By by) {
		return getDriver().findElement(by).getText();
	}
	
	public String precoDoProdutoMercadoLivreSymbol(By by) {
		return getDriver().findElement(by).getText();
	}
	public String precoDoProdutoMercadoLivreFraction(By by) {
		return getDriver().findElement(by).getText();
	}
	public String precoDoProdutoMercadoLivreSeparator(By by) {
		return getDriver().findElement(by).getText();
	}
	public String precoDoProdutoMercadoLivreCents(By by) {
		return getDriver().findElement(by).getText();
	}


	public String descontoDoProduto(By by) {
		return getDriver().findElement(by).getText();
	}

	public String vendedorDoProduto(By by) {
		return getDriver().findElement(by).getText();
	}

	public String estoqueDoProduto(By by) {
		return getDriver().findElement(by).getText();
	}

	public String compraRetirarProdutoHj(By by) {
		return getDriver().findElement(by).getText();
	}
	public String produto(By by, String name) {
		return getDriver().findElement(by).getAttribute(name);
	}
	


	public void clicaNoProduto(By by, By by2) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 1);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		wait.until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = getDriver().findElement(by);
		
		Actions actions = new Actions(getDriver());
		actions.moveToElement(element);
		actions.perform();
		actions.build();
		wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by2));
		element.click();
		
	}
	
	public void esperar(By by) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void clicaNoProdutoLeroy(By by) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 1);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		wait.until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = getDriver().findElement(by);
		
		Actions actions = new Actions(getDriver());
		actions.moveToElement(element);
		actions.perform();
		actions.build();
//		wait = new WebDriverWait(getDriver(), 10);
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(by2));
		element.click();
	}
	
	public void clicaNoProdutoMercadoLivre(By by) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		wait.until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = getDriver().findElement(by);
		
		Actions actions = new Actions(getDriver());
		actions.moveToElement(element);
		actions.perform();
		actions.build();
//		wait = new WebDriverWait(getDriver(), 10);
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(by2));
		wait.until(ExpectedConditions.elementToBeClickable(by));
		element.click();

		
	}
	

}
