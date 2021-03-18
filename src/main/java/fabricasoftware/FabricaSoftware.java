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
		
		//Esse comando prepara o ambiente para o início do teste, utilizando o browser do Chrome, por isso "chromedriver".
		WebDriverManager.chromedriver().setup();
		//Inicializando o driver em uma variável.
		WebDriver driver = new ChromeDriver();
		//Esse comando indica para o driver que ele utilizará o site abaixo.
		driver.get("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
		
		//Comando padrão para identificar qual é o elemento no site que eu quero guardar nessa variável.
		//A parte "findElement" é responsável por especificar qual parte será procurada na página, e existem várias formas de espeficar essa procura.
		//O nome desse procedimento é mapeamento.
		
		WebElement userNameBox = driver.findElement(By.name("username"));
		WebElement passwordBox = driver.findElement(By.name("password"));
		WebElement commentsBox = driver.findElement(By.name("comments")); 
		
		//Agora vamos mapear a parte das checkbox, que é necessário usar outro método de encontrar no browser
		//Como o nome da caixa de marcação se repete entre todas as caixas, ele não é único para identificar uma unidade apenas.
		//Portanto, é necessário usar o "value" que é único, usando o xpath.
		WebElement checkbox1 = driver.findElement(By.xpath("//input[@value='cb1']"));
		WebElement checkbox2 = driver.findElement(By.xpath("//input[@value='cb2']"));
		WebElement checkbox3 = driver.findElement(By.xpath("//input[@value='cb3']"));
		
		//Radio Buttons
		WebElement radioButton2 = driver.findElement(By.xpath("//input[@value='rd2']"));
		
		//Multiple Select Values
		//Nesse caso específico, ele não é um tag input. A tag é select e por isso precisamos separar a tag inteira para em seguida pegar as unidades chamadas "options"
		//A tag Select vem pro java como array, e os valores dentro dele são options.
		WebElement multipleSelect = driver.findElement(By.name("multipleselect[]"));
		Select selectMultipleSelect = new Select(multipleSelect);
		
		//Dropdown
		WebElement dropdown = driver.findElement(By.name("dropdown"));
		Select selectDropDown = new Select(dropdown);
		
		//Submit Button
		//Não pode ser selecionado pelo "name" porque o botão de cancel também tem o mesmo nome, logo não é único.
		WebElement submitButton = driver.findElement(By.xpath("//input[@value='submit']"));
		
		
		// *** PREENCHIMENTO DOS ELEMENTOS SELECIONADOS ***
		
		
		//Agora vamos realizar o preenchimento do formulário, colocando as informações desejadas.
		//Esse comando serve pra selecionar o campo a variável e escrever nele.
		userNameBox.sendKeys("QA Name");
		passwordBox.sendKeys("QA Password");
		//O campo de comentários já veio com texto, foi necessário limpa-lo antes de escrever.
		commentsBox.clear();
		commentsBox.sendKeys("Comentario");
				
		//O comando pra clicar em uma caixinha é o abaixo
		checkbox1.click();
		checkbox2.click();
		//Como o checkbox3 já veio preenchido por padrão, não vamos clicar para preenche-lo.
		radioButton2.click();
		
		//É aqui que selecionamos o valor do select ("options")
		//Como a função select já é possível encontrar por value, não precisamos usar o xpath, colocando o valor.
		selectMultipleSelect.selectByValue("ms4");
		selectDropDown.selectByValue("dd3");
		
		submitButton.click();
	
		
		// *** VALIDAÇÃO ***
		//No AssertEquals passamos dois valores, o primeiro é o que esperamos de resultado e o segundo é o resultado real.
		//O driver.findElement é justamente a procura no site pelo valor do resultado a partir do seu ID, sendo o getText a forma de pegar o conteúdo de dentro daquela tag.
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
		
		// *** VALIDAÇÃO ***
		//Foi necessário utilizar o xpath pois não havia campo de válido para validação. Ou seja, quando não digitamos um nome o site nos dá como resposta esse "No Value for usarname", e por isso decimos validar através desse texto.
		Assert.assertEquals("No Value for username", driver.findElement(By.xpath("//strong[text()=\"No Value for username\"]")).getText());
		Assert.assertEquals("No Value for password", driver.findElement(By.xpath("//strong[text()=\"No Value for password\"]")).getText());
		// Como no formulário a parte de comentário já veio preenchida, ele terá valor pra ser validado.
		Assert.assertEquals("Comments...", driver.findElement(By.id("_valuecomments")).getText());

		// *** FECHAMENTO DO DRIVER ***
		
		
		driver.close();
		
		
	}
}
