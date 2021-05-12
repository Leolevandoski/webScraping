package americanasTest;

import static com.leo.core.DriverFactory.getDriver;
import static com.leo.core.DriverFactory.killDriver;
import static com.leo.core.ProgramExe.produto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.leo.core.DSL;

public class Americanas {

	private DSL dsl;

	private final String NAME = "product-name-default";
	private final String PRICE = "//*[@id=\"content\"]/div/div/div[2]/div/section/div/div[2]/div[2]/div/div[1]/div[1]/div/div[1]/div/div/span";
	private final String DISCOUNT = "//*[@id=\"content\"]/div/div/div[2]/div/section/div/div[2]/div[2]/div/div[1]/div[1]/div/div[1]/div/div[2]/span";
	private final String SELLER = "//*[@id=\"content\"]/div/div/div[2]/div/section/div/div[2]/div[2]/div/div[2]/div/div[2]/span";
	private final String ESTOQUE = "title-stock";
	private final String RETIRARHJ = "//*[@id=\"content\"]/div/div/div[2]/div/section/div/div[2]/div[2]/div/div/div[1]/h3";
	private final String QUANTIDADE = "//*[@id=\"sort-bar\"]/div/aside/div/div[1]/span";
	
//	public static void main(String[] args) throws IOException {

	@Test

	public void test() throws IOException, InterruptedException {

		dsl = new DSL();
		
		String product = "torneira";
//		String product = produto();
		int next = 2;
		int cont = 2;

//		----------CONFIGURACOES----------

		dsl.bufferedConfig();

//		----------SITE----------

		getDriver().get("https://www.americanas.com.br");
		System.out.println(getDriver().getTitle());
		System.out.println(getDriver().getCurrentUrl());

//		----------Pesquisa----------

//		WebElement searchAmericanas = driver.findElement(By.cssSelector("id='h_search-input']"));
//		searchAmericanas.sendKeys(product);
//		searchAmericanas.submit();
		getDriver().findElement(By.id("h_search-input")).sendKeys(product);
		getDriver().findElement(By.id("h_search-btn")).click();

//		----------Quantidade de Produto----------

		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(QUANTIDADE)));
			String quantidadeProduto = dsl.quantidadeDoProduto(By.xpath(QUANTIDADE));
			System.out.println(quantidadeProduto);
			dsl.escreve(quantidadeProduto);
			dsl.pulaLinha();

		} catch (NoSuchElementException e) {

		} catch (org.openqa.selenium.TimeoutException ex) {

		}
		
		System.out.println("Running...");

//		----------TABELA----------
		dsl.tabela();

//		----------SELECIONA O PRODUTO----------

		while (next == cont) {

			for (int i = 1; i <= 24; i++) {

				try {
									

					dsl.clicaNoProduto(By.xpath("//*[@id=\"content-middle\"]/div[6]/div/div/div/div[1]/div[" + i
							+ "]/div/div[2]/a/section/div[2]/div[1]/h2"), By.className("loading-bar-overlay"));

				} catch (org.openqa.selenium.StaleElementReferenceException ex) {
					
					
					dsl.clicaNoProduto(By.xpath("//*[@id=\"content-middle\"]/div[6]/div/div/div/div[1]/div[" + i
							+ "]/div/div[2]/a/section/div[2]/div[1]/h2"), By.className("loading-bar-overlay"));

				} catch (NoSuchElementException e) {
				
				} catch (org.openqa.selenium.TimeoutException ex) {

				}

//				----------NOME DO PRODUTO----------

				try {

					String name = dsl.nomeDoProduto(By.id(NAME));

					dsl.escrevePulaColuna(name);

				} catch (NoSuchElementException e) {

				}

//				----------PREÇO DO PRODUTO----------

				try {

					String price = dsl.precoDoProduto(By.xpath(PRICE));

					dsl.escrevePulaColuna(price);

				} catch (NoSuchElementException e) {

				}

//				----------DESCONTO DO PRODUTO----------

				try {

					String discount = dsl.descontoDoProduto(By.xpath(DISCOUNT));
					dsl.escrevePulaColuna(discount);

				} catch (NoSuchElementException e) {

					dsl.pulaColuna();
				}

//				----------VENDEDOR DO PRODUTO----------

				try {

					String seller = dsl.vendedorDoProduto(By.xpath(SELLER));
					dsl.escrevePulaColuna(seller);

					dsl.pulaLinha();
					getDriver().navigate().back();
				} catch (NoSuchElementException e) {

				}

//				----------PRODUTO SEM ESTOQUE----------

				try {
					String estoque = dsl.estoqueDoProduto(By.id(ESTOQUE));
					dsl.escrevePulaColuna(estoque);

					dsl.pulaLinha();
					getDriver().navigate().back();
				} catch (NoSuchElementException e) {

				}

//				----------TAG RETIRAR HOJE----------

				try {
					String compraRetireHj = dsl.compraRetirarProdutoHj(By.xpath(RETIRARHJ));
					dsl.escreve(compraRetireHj);
					dsl.pulaLinha();

					getDriver().navigate().back();
				} catch (NoSuchElementException e) {

				}

				dsl.flush();

			}

//			----------PROXIMA PAGINA----------

			try {
				JavascriptExecutor js = (JavascriptExecutor) getDriver();
				js.executeScript("window.scrollTo (0, document.body.scrolHeight)");
				next++;
				WebElement nextPage = getDriver().findElement(
						By.xpath("//*[@id=\"content-middle\"]/div[6]/div/div/div/div[2]/div/ul/li[" + next + "]/a"));

//				Actions actions = new Actions(driver);
//				actions.moveToElement(nextPage);
//				actions.build();
//				actions.perform();		
				nextPage.click();

				WebDriverWait wait = new WebDriverWait(getDriver(), 10);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading-bar-overlay")));

				dsl.escreve("Page" + cont);

				dsl.pulaLinha();

			} catch (org.openqa.selenium.ElementClickInterceptedException ex) {
				System.out.println("Ultima Pagina");
				next++;
				System.out.println("....The End....");
			}

			catch (NoSuchElementException e) {

				System.out.println("Ultima Pagina");
				next++;
				System.out.println("....The End....");
			}

			cont++;
		}

//		----------FINAL----------

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataDeHoje = new Date();
		dsl.pulaLinha();

		dsl.escreve("Data: " + formato.format(dataDeHoje));

		dsl.flush();

		dsl.fecharBuffered();
		killDriver();
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");

	}

}
