package americanasTest;

import static com.leo.core.DriverFactory.getDriver;
import static com.leo.core.DriverFactory.killDriver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.leo.core.DSL;

public class MercadoLivre {

	private DSL dsl;

	private final String NAME = "h1[class='item-title__primary ']";
	private final String PRICE = "span[class='price-tag']";
	private final String DISCOUNT = "//*[@id=\"productInfo\"]/fieldset[1]/span[2]";
	private final String SELLER = "//*[@id=\"root-app\"]/div/div[1]/div[2]/div[1]/section[2]/p";
	private final String ESTOQUE = "//*[@id=\"dropdown-quantity\"]/button/span[4]";
	private final String QUANTIDADE = "quantity-results";

	private final String SYMBOL = "span[class='price-tag-symbol']";
	private final String FRACTION = "span[class='price-tag-fraction']";
	private final String SEPARADOR = "span[class='price-tag-decimal-separator']";
	private final String CENTS = "span[class='price-tag-cents']";

//	public static void main(String[] args) throws IOException {

	@Test

	public void test() throws IOException, InterruptedException {

		dsl = new DSL();

		String product = "parafusadeira furadeira 12v 3/8 litio biv 5498 titanium +kit";
//		String product = produto();
		int next = 2;
		int cont = 2;

//		----------CONFIGURACOES----------

		dsl.bufferedConfig();

//		----------SITE----------

		getDriver().get("https://www.mercadolivre.com.br/");
		System.out.println(getDriver().getTitle());
		System.out.println(getDriver().getCurrentUrl());

//		----------Pesquisa----------

		WebElement searchAmericanas = getDriver().findElement(By.cssSelector("input[class='nav-search-input']"));
		searchAmericanas.sendKeys(product);
		searchAmericanas.submit();
//		getDriver().findElement(By.className("nav-search-input")).sendKeys(product);
//		getDriver().findElement(By.className("nav-search-input")).submit();

//		----------Quantidade de Produto----------

		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(QUANTIDADE)));
			String quantidadeProduto = dsl.quantidadeDoProduto(By.className(QUANTIDADE));
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
			List<WebElement> contagemDaLista = getDriver()
					.findElements(By.xpath("//ol[@id='searchResults']/li/div/div/div/h2/a"));
			int quantidadeDeProduto = contagemDaLista.size();
			System.out.println(quantidadeDeProduto);
			quantidadeDeProduto = quantidadeDeProduto - 1;
			for (int i = 0; i <= quantidadeDeProduto; i++) {
				System.out.println(i);
				try {
					List<WebElement> linksProdutos = getDriver()
							.findElements(By.xpath("//ol[@id='searchResults']/li/div/div/div/h2/a"));
					linksProdutos.get(i).click();

				} catch (org.openqa.selenium.StaleElementReferenceException ex) {

				} catch (NoSuchElementException e) {

				} catch (org.openqa.selenium.TimeoutException ex) {

				}

				try {
					WebElement clickFull = getDriver()
							.findElement(By.cssSelector("a[class='ui-button ok-btn ui-button--primary']"));
					clickFull.click();

				} catch (NoSuchElementException e) {

				} catch (ElementNotInteractableException e) {

				}
				try {
					WebElement clickFullImg = getDriver()
							.findElement(By.cssSelector("span[class='ch-icon ch-icon-search']"));
					clickFullImg.click();

				} catch (NoSuchElementException e) {

				} catch (ElementNotInteractableException e) {

				}

//				----------NOME DO PRODUTO----------

				try {

					dsl.esperar(By.cssSelector(NAME));

					String name = dsl.nomeDoProduto(By.cssSelector(NAME));

					dsl.escrevePulaColuna(name);

				} catch (NoSuchElementException e) {

				} catch (org.openqa.selenium.TimeoutException ex) {

				}

//				----------PREÇO DO PRODUTO----------

				try {
					dsl.esperar(By.cssSelector(PRICE));

					String symbol = dsl.precoDoProdutoMercadoLivreSymbol(By.cssSelector(SYMBOL));
					String fraction = dsl.precoDoProdutoMercadoLivreFraction(By.cssSelector(FRACTION));
					String separador = dsl.precoDoProdutoMercadoLivreSeparator(By.cssSelector(SEPARADOR));
					String cents = dsl.precoDoProdutoMercadoLivreCents(By.cssSelector(CENTS));

					dsl.escrevePulaColuna(symbol + fraction + separador + cents);

				} catch (NoSuchElementException e) {

				} catch (org.openqa.selenium.TimeoutException ex) {

				}

//				----------DESCONTO DO PRODUTO----------

				try {

					dsl.esperar(By.xpath(DISCOUNT));

					String symbol = dsl.precoDoProdutoMercadoLivreSymbol(
							By.xpath("//*[@id=\"productInfo\"]/fieldset[1]/span[2]/span[1]"));
					String fraction = dsl.precoDoProdutoMercadoLivreFraction(
							By.xpath("//*[@id=\"productInfo\"]/fieldset[1]/span[2]/span[2]"));
					String separador = dsl.precoDoProdutoMercadoLivreSeparator(
							By.xpath("//*[@id=\"productInfo\"]/fieldset[1]/span[2]/span[3]"));
					String cents = dsl.precoDoProdutoMercadoLivreCents(
							By.xpath("//*[@id=\"productInfo\"]/fieldset[1]/span[2]/span[4]"));

					dsl.escrevePulaColuna(symbol + fraction + separador + cents);

				} catch (NoSuchElementException e) {

					dsl.pulaColuna();
				} catch (org.openqa.selenium.TimeoutException ex) {
					dsl.pulaColuna();
				}

//				----------VENDEDOR DO PRODUTO----------
				String winHandleBefore = getDriver().getWindowHandle();
				try {
//					dsl.esperar(By.xpath(SELLER));

//					String seller = dsl.vendedorDoProduto(By.id("store-info__name"));

					winHandleBefore = getDriver().getWindowHandle();

					getDriver().findElement(By.id("seller-view-more-link")).click();

					for (String winHandle : getDriver().getWindowHandles()) {
						getDriver().switchTo().window(winHandle);

					}
					dsl.esperar(By.cssSelector("h3[id='store-info__name']"));
					String seller = getDriver().findElement(By.cssSelector("h3[id='store-info__name']")).getText();

					getDriver().close();

					getDriver().switchTo().window(winHandleBefore);

					dsl.escrevePulaColuna(seller);
					dsl.pulaLinha();

					getDriver().navigate().back();

				} catch (NoSuchElementException e) {

					dsl.esperar(By.xpath("//*[@id=\"brand\"]/span/span"));
					String seller = getDriver().findElement(By.xpath("//*[@id=\"brand\"]/span/span")).getText();

					getDriver().close();

					getDriver().switchTo().window(winHandleBefore);

					dsl.escrevePulaColuna(seller);
					dsl.pulaLinha();

					getDriver().navigate().back();

				} catch (org.openqa.selenium.TimeoutException ex) {

					dsl.esperar(By.xpath("//*[@id=\"brand\"]/span/span"));
					String seller = getDriver().findElement(By.xpath("//*[@id=\"brand\"]/span/span")).getText();

					getDriver().close();

					getDriver().switchTo().window(winHandleBefore);

					dsl.escrevePulaColuna(seller);
					dsl.pulaLinha();

					getDriver().navigate().back();

				}

//				----------PRODUTO SEM ESTOQUE----------

				try {
					dsl.esperar(By.xpath(ESTOQUE));
					String estoque = dsl.estoqueDoProduto(By.xpath(ESTOQUE));
					dsl.escrevePulaColuna(estoque);

					dsl.pulaLinha();
					getDriver().navigate().back();
				} catch (NoSuchElementException e) {

				} catch (org.openqa.selenium.TimeoutException ex) {

				}

				dsl.flush();

			}

//			----------PROXIMA PAGINA----------

			try {
				JavascriptExecutor js = (JavascriptExecutor) getDriver();
				js.executeScript("window.scrollTo (0, document.body.scrolHeight)");
				next++;
				WebElement nextPage = getDriver()
						.findElement(By.cssSelector("span[class='andes-pagination__arrow-title'"));

//				Actions actions = new Actions(driver);
//				actions.moveToElement(nextPage);
//				actions.build();
//				actions.perform();		
				nextPage.click();

//				WebDriverWait wait = new WebDriverWait(getDriver(), 10);
//				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading-bar-overlay")));

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
