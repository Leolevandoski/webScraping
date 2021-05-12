package americanasTest;

import static com.leo.core.DriverFactory.getDriver;
import static com.leo.core.DriverFactory.killDriver;
//import static com.leo.core.ProgramExe.produto;


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

public class LeroyMerlin {

	private DSL dsl;

	private final String NAME = "/html/body/div[7]/div/div[1]/div[2]/div[1]/h1";
	private final String PRICE = "/html/body/div[7]/div/div[1]/div[3]/div[1]/div[1]/div/div/div";
	private final String DISCOUNT = "/html/body/div[7]/div/div[1]/div[3]/div[1]/div[1]/div/div[2]/span";
	private final String SELLER = "/html/body/div[7]/div/div[1]/div[3]/section/span[2]";
	private final String SELLER2 = "buybox-seller-info";
	private final String ESTOQUE = "p[class='soldout-title-message']";

//	public static void main(String[] args) throws IOException {

	@Test

	public void test() throws Exception {

		dsl = new DSL();

		String product = "torneira goa";
		String findWord = "Goa";
//		String product = produto();
		int next = 3;
		int cont = 3;
		int pag = 1;

//		----------CONFIGURACOES----------

		dsl.bufferedConfig();

//		----------SITE----------

		getDriver().get("https://www.leroymerlin.com.br/localizacao");

		System.out.println(getDriver().getTitle());
		System.out.println(getDriver().getCurrentUrl());

		WebElement element = getDriver().findElement(By.name("zipcode"));
		element.sendKeys("80050490");
		Thread.sleep(2000L);
		System.out.println("***********************************confirm dialog box****************************");
		getDriver().findElement(By.xpath("/html/body/div[1]/div/form/div/div[2]/button")).click();
		Thread.sleep(2000L);

//		----------Pesquisa----------

		try {
			dsl.esperar(By.className("search-input"));

			getDriver().findElement(By.className("search-input")).sendKeys(product);
			getDriver().findElement(By.className("search-input")).submit();
		} catch (org.openqa.selenium.TimeoutException ex) {

		}

		System.out.println("Running...");

//		----------TABELA----------
		dsl.tabela();

//		----------SELECIONA O PRODUTO----------

		while (next == cont) {

			dsl.esperar(By.cssSelector("div[class='thumbnail thumbnail-hover product-thumb  ']"));
			int elementsCount = getDriver()
					.findElements(By.cssSelector("div.thumbnail.thumbnail-hover.product-thumb  ")).size();
			System.out.println("Amount of elements: " + elementsCount);

			for (int i = 1; i <= elementsCount; i++) {

				
				
				if (dsl.produto(By.xpath(
						"/html/body/div[4]/div[5]/div[2]/div/div/div/div[" + i + "]"), "title")
						.contains(findWord)) {					
			
					
					try {

						dsl.clicaNoProdutoLeroy(By.xpath("/html/body/div[4]/div[5]/div[2]/div/div/div/div[" + i
								+ "]/figure/div[2]/figcaption/a/div[1]"));

						WebDriverWait wait = new WebDriverWait(getDriver(), 10);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(
								By.cssSelector("div[class='overlay visible show-spinner']")));
					} catch (org.openqa.selenium.StaleElementReferenceException ex) {

						dsl.clicaNoProdutoLeroy(By.xpath("/html/body/div[4]/div[5]/div[2]/div/div/div/div[" + i
								+ "]/div/div[2]/figcaption/a/div[1]"));
//					} catch (org.openqa.selenium.StaleElementReferenceException ex) {
//
//						dsl.clicaNoProdutoLeroy(
//								By.xpath("/html/body/div[4]/div[5]/div[2]/div/div/div/div[" + i
//										+ "]/figure/div[2]/figcaption/a/div[1]"),
//								By.className("overlay visible show-spinner"));

					} catch (NoSuchElementException e) {

					} catch (org.openqa.selenium.TimeoutException ex) {

					}

					System.out.println(i);

//				----------NOME DO PRODUTO----------

					try {
						dsl.esperar(By.xpath(NAME));
						String name = dsl.nomeDoProduto(By.xpath(NAME));

						dsl.escrevePulaColuna(name);

					} catch (NoSuchElementException e) {

					} catch (org.openqa.selenium.TimeoutException ex) {

					}
//				----------PREÇO DO PRODUTO----------

					try {
						dsl.esperar(By.xpath(PRICE));
						String price = dsl.precoDoProduto(By.xpath(PRICE));

						dsl.escrevePulaColuna(price);

					} catch (NoSuchElementException e) {
						dsl.pulaColuna();

					} catch (org.openqa.selenium.TimeoutException ex) {

					}

//				----------DESCONTO DO PRODUTO----------

					try {

						String discount = dsl.descontoDoProduto(By.xpath(DISCOUNT));
						dsl.escrevePulaColuna(discount);

					} catch (NoSuchElementException e) {

						
					}

//				----------PRODUTO SEM ESTOQUE----------

					try {

						String estoque = dsl.estoqueDoProduto(By.cssSelector(ESTOQUE));
						dsl.escrevePulaColuna(estoque);

					} catch (NoSuchElementException e) {
						dsl.pulaColuna();

					}

//				----------VENDEDOR DO PRODUTO----------

					try {

						try {

							String seller = dsl.vendedorDoProduto(By.xpath(SELLER));
							dsl.escrevePulaColuna(seller);
							dsl.pulaLinha();
							getDriver().navigate().back();

						} catch (Exception e) {
							String seller2 = dsl.vendedorDoProduto(By.className(SELLER2));
							dsl.escrevePulaColuna(seller2);
							dsl.pulaLinha();
							getDriver().navigate().back();
						}

					} catch (NoSuchElementException e) {

					}

					dsl.flush();
				} else {
					System.out.println(i);
				}

			}

//			----------PROXIMA PAGINA----------

			try {
				JavascriptExecutor js = (JavascriptExecutor) getDriver();
				js.executeScript("window.scrollTo (0, document.body.scrolHeight)");
				next++;
				WebElement nextPage = getDriver()
						.findElement(By.xpath("/html/body/div[4]/div[5]/div[2]/nav/a[" + next + "]"));

//				Actions actions = new Actions(getDriver());
//				actions.moveToElement(nextPage);
//				actions.build();
//				actions.perform();		
				nextPage.click();

				WebDriverWait wait = new WebDriverWait(getDriver(), 10);
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(By.cssSelector("div[class='overlay visible show-spinner']")));
				pag++;
				dsl.escreve("Page" + pag);

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

			} catch (org.openqa.selenium.TimeoutException ex) {

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
