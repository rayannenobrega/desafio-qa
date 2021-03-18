package fabricasoftware;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FabricaSoftware {
	
	@BeforeClass
	public static void preparandoTeste() {
	}
	
	@AfterClass
	public static void finalizandoTeste() {
	}
	
	@Test
	public void fluxoFeliz() {
		
		// *** MAPEANDO OS ELEMENTOS DA PAGINA ***
		
		//Esse comando prepara o ambiente para o in�cio do teste, utilizando o browser do Chrome, por isso "chromedriver".
		WebDriverManager.chromedriver().setup();
		//Inicializando o driver em uma vari�vel.
		WebDriver driver = new ChromeDriver();
		//Esse comando indica para o driver que ele utilizar� o site abaixo.
		driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
		
		//Comando padr�o para identificar qual � o elemento no site que eu quero guardar nessa vari�vel.
		//A parte "findElement" � respons�vel por especificar qual parte ser� procurada na p�gina, e existem v�rias formas de espeficar essa procura.
		//O nome desse procedimento � mapeamento.
		
		WebElement userNameBox = driver.findElement(By.name("username"));
		WebElement passwordBox = driver.findElement(By.name("password"));
		WebElement commentsBox = driver.findElement(By.name("comments")); 
		
		//Agora vamos mapear a parte das checkbox, que � necess�rio usar outro m�todo de encontrar no browser
		//Como o nome da caixa de marca��o se repete entre todas as caixas, ele n�o � �nico para identificar uma unidade apenas.
		//Portanto, � necess�rio usar o "value" que � �nico, usando o xpath.
		WebElement checkbox1 = driver.findElement(By.xpath("//input[@value='cb1']"));
		WebElement checkbox2 = driver.findElement(By.xpath("//input[@value='cb2']"));
		WebElement checkbox3 = driver.findElement(By.xpath("//input[@value='cb3']"));
		
		//Radio Buttons
		WebElement radioButton2 = driver.findElement(By.xpath("//input[@value='rd2']"));
		
		//Multiple Select Values
		//Nesse caso espec�fico, ele n�o � um tag input. A tag � select e por isso precisamos separar a tag inteira para em seguida pegar as unidades chamadas "options"
		//A tag Select vem pro java como array, e os valores dentro dele s�o options.
		WebElement multipleSelect = driver.findElement(By.name("multipleselect[]"));
		Select selectMultipleSelect = new Select(multipleSelect);
		
		//Dropdown
		WebElement dropdown = driver.findElement(By.name("dropdown"));
		Select selectDropDown = new Select(dropdown);
		
		//Submit Button
		//N�o pode ser selecionado pelo "name" porque o bot�o de cancel tamb�m tem o mesmo nome, logo n�o � �nico.
		WebElement submitButton = driver.findElement(By.xpath("//input[@value='submit']"));
		
		
		// *** PREENCHIMENTO DOS ELEMENTOS SELECIONADOS ***
		
		
		//Agora vamos realizar o preenchimento do formul�rio, colocando as informa��es desejadas.
		//Esse comando serve pra selecionar o campo a vari�vel e escrever nele.
		userNameBox.sendKeys("QA Name");
		passwordBox.sendKeys("QA Password");
		//O campo de coment�rios j� veio com texto, foi necess�rio limpa-lo antes de escrever.
		commentsBox.clear();
		commentsBox.sendKeys("Comentario");
				
		//O comando pra clicar em uma caixinha � o abaixo
		checkbox1.click();
		checkbox2.click();
		//Como o checkbox3 j� veio preenchido por padr�o, n�o vamos clicar para preenche-lo.
		radioButton2.click();
		
		//� aqui que selecionamos o valor do select ("options")
		//Como a fun��o select j� � poss�vel encontrar por value, n�o precisamos usar o xpath, colocando o valor.
		selectMultipleSelect.selectByValue("ms4");
		selectDropDown.selectByValue("dd3");
		
		submitButton.click();
	
		
		// *** VALIDA��O ***
		//No AssertEquals passamos dois valores, o primeiro � o que esperamos de resultado e o segundo � o resultado real.
		//O driver.findElement � justamente a procura no site pelo valor do resultado a partir do seu ID, sendo o getText a forma de pegar o conte�do de dentro daquela tag.
		Assert.assertEquals("QA Name", driver.findElement(By.id("_valueusername")).getText());
		Assert.assertEquals("QA Password", driver.findElement(By.id("_valuepassword")).getText());
		Assert.assertEquals("Comentario", driver.findElement(By.id("_valuecomments")).getText());
		Assert.assertEquals("cb1", driver.findElement(By.id("_valuecheckboxes0")).getText());
		Assert.assertEquals("cb2", driver.findElement(By.id("_valuecheckboxes1")).getText());
		Assert.assertEquals("cb3", driver.findElement(By.id("_valuecheckboxes2")).getText());
		Assert.assertEquals("rd2", driver.findElement(By.id("_valueradioval")).getText());
		Assert.assertEquals("ms4", driver.findElement(By.id("_valuemultipleselect0")).getText());
		Assert.assertEquals("dd3", driver.findElement(By.id("_valuedropdown")).getText());
		Assert.assertEquals("submit", driver.findElement(By.id("_valuesubmitbutton")).getText());
		
		
		// *** FECHAMENTO DO DRIVER ***
		
		
		driver.close();
	}

	@Test
	public void fluxoAlternativo() {
		
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
		
		// *** MAPEANDO OS ELEMENTOS DA PAGINA ***
		WebElement submitButton = driver.findElement(By.xpath("//input[@value='submit']"));
		
		// *** PREENCHIMENTO DOS ELEMENTOS SELECIONADOS ***
		submitButton.click();
		
		// *** VALIDA��O ***
		//Foi necess�rio utilizar o xpath pois n�o havia campo de v�lido para valida��o. Ou seja, quando n�o digitamos um nome o site nos d� como resposta esse "No Value for usarname", e por isso decimos validar atrav�s desse texto.
		Assert.assertEquals("No Value for username", driver.findElement(By.xpath("//strong[text()=\"No Value for username\"]")).getText());
		Assert.assertEquals("No Value for password", driver.findElement(By.xpath("//strong[text()=\"No Value for password\"]")).getText());
		// Como no formul�rio a parte de coment�rio j� veio preenchida, ele ter� valor pra ser validado.
		Assert.assertEquals("Comments...", driver.findElement(By.id("_valuecomments")).getText());

		// *** FECHAMENTO DO DRIVER ***
		
		
		driver.close();
		
		
	}
}
