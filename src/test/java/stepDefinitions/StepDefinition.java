package stepDefinitions;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class StepDefinition {
	
	private String path = System.getProperty("user.dir") + "\\src\\test\\java\\endToEndCases\\NatOnlineShop\\resourses\\chromedriver.exe";
    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;
    private static String name = "Москва, Центр, Россия";
    private WebDriverWait wait;
    
	@Given("^User is on the main page$")
	public void user_is_on_the_main_page() {
		
		System.setProperty("webdriver.chrome.driver", path);
		driver= new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("*");
	}
	
	@When("^User chooses item from catalog and choose it's amount")
	public void user_chooses_item_from_catalog_and_choose_it_s_amount() {
		
		
        WebElement sweetsButton = driver.findElement(By.xpath("//nav/div/div/ul/li/div[1]/a[@href='/catalog/konfety/']"));
        WebElement kedrGrButton = driver.findElement(By.xpath
                ("//nav/div/div/ul/li/div[2]/ul/li/div[1]/a[@href='/catalog/kedrovyy_grilyazh/']"));
        WebElement kedrGrWeightButton = driver.findElement(By.xpath
                ("//nav/div/div/ul/li/div[2]/ul/li/div[2]/ul/li[1]/div/a[@href='/catalog/batonchiki_kedrovyy_grilyazh/']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(sweetsButton).moveToElement(kedrGrButton)
                .moveToElement(kedrGrWeightButton).click();
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("bx_3966226736_1434_7e1b8e3524755c391129a9d7e6f2d206")));
        WebElement kedrAmta45g = driver.findElement(By.id("bx_3966226736_1434_7e1b8e3524755c391129a9d7e6f2d206"));
        WebElement plusButton = driver.findElement(By.id("bx_3966226736_1434_7e1b8e3524755c391129a9d7e6f2d206_quant_up"));
        WebElement kedrAmta45gToBasket = driver.findElement
                (By.id("bx_3966226736_1434_7e1b8e3524755c391129a9d7e6f2d206_buy_link"));
        actions.moveToElement(kedrAmta45g).click().moveToElement(plusButton).click().click()
                .moveToElement(kedrAmta45gToBasket).click().perform();
	}
	
	@Then("^User goes to cart$")
	public void user_goes_to_cart() {
		By goToBasket = By.xpath("//div/div/div[3]/a[2]");
		wait = new WebDriverWait(driver, 15);
        wait.until((WebDriver dr) -> dr.findElement(goToBasket)).click();
		
	}
	
	@And("^User submits the order and goes futher$")
	public void user_submits_the_order_and_goes_futher() {
		javascriptExecutor = (JavascriptExecutor) driver;
		
        javascriptExecutor.executeScript("window.scrollBy(0, 3000)");
        wait = new WebDriverWait(driver, 15);
        By checkOutButton = By.cssSelector("button[data-entity='basket-checkout-button']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkOutButton)).click();

        
		
	}
	@And("^User fills in delivery form - chooses the region from given$")
	public void user_fills_in_delivery_form_chooses_the_region_from_given() {
		javascriptExecutor = (JavascriptExecutor) driver;
		
		javascriptExecutor.executeScript("window.scrollBy(0, 1500)");
        By goAheadBasketButton = By.xpath("//div[@id='bx-soa-basket']/div[2]/div[3]/div/a");
        System.out.println(wait.until(ExpectedConditions.visibilityOfElementLocated(goAheadBasketButton)).getText());
		int i = 0;
        while (i < 3) {
            try {
                driver.findElement(goAheadBasketButton).click();
                System.out.println("Everything good");
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Something wrong");
                i++;
            }catch (ElementClickInterceptedException e) {
                System.out.println("Something wrong ElementClickInterceptedException");
                i++;
            }
        }
        WebElement payerRadioButton = driver.findElement(By.cssSelector("input[checked='true']"));
        if (payerRadioButton.isSelected()) {
            System.out.println("RadioButton of the private payer is selected");
        } else {
            System.out.println("Payer button is not selected");
        }

        WebElement cityChoose = driver.findElement(By.cssSelector("input[class='bx-ui-sls-fake']"));
        cityChoose.clear();
        cityChoose.sendKeys("мос");
        wait  = new WebDriverWait(driver, 15);
        By cities = By.cssSelector("span[class='dropdown-item-text']");
        wait.until((WebDriver dr) ->dr.findElement(cities));
        List<WebElement> getCities = driver.findElements(cities);
        for(WebElement city : getCities) {
            if (city.getText().equals(name)) {
                city.click();
                break;
            }
        }

        By goAheadRegionButton = By.xpath("//div[@id='bx-soa-region']/div[2]/div[3]/div/a[2]");
        System.out.println(wait.until(ExpectedConditions.presenceOfElementLocated(goAheadRegionButton)).getText());

        i = 0;
        while (i < 3) {
            try {
                driver.findElement(goAheadRegionButton).click();
                System.out.println("Everything good");
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Something wrong");
                i++;
            }catch (ElementClickInterceptedException e) {
                System.out.println("Something wrong ElementClickInterceptedException");
                i++;
            }
        }
	}
	
	@And("^User fills in delivery form - chooses shipping company from given$")
	public void user_fills_in_delivery_form_chooses_shipping_company_from_given() throws InterruptedException {
		
		WebElement deliveryDefault = driver.findElement
                (By.cssSelector("input#ID_DELIVERY_ID_1"));
        if(deliveryDefault.isSelected()) {
            System.out.println("Delivery default is selected");
        }
        else {
            System.out.println("Delivery default is not selected");
        }
        By goAheadDeliveryButton = By.xpath("//div[@id='bx-soa-delivery']/div[2]/div[4]/div/a[2]");
        WebElement AheadDeliveryButton = wait.until(ExpectedConditions.visibilityOfElementLocated(goAheadDeliveryButton));
        Thread.sleep(3000);
        System.out.println(AheadDeliveryButton.getText());
        AheadDeliveryButton.click();
		
	}
	@And("^User fills in delivery form - chooses payment method from given$")
	public void user_fills_in_delivery_form_chooses_payment_method_from_given() throws InterruptedException {
		 WebElement sberBankButton = driver.findElement
	                (By.xpath("//div[@class='bx-soa-pp row']/div/div/div/input[@id='ID_PAY_SYSTEM_ID_10']"));
	        if(sberBankButton.isSelected()) {
	            System.out.println("SberBank option is selected by default");
	        }
	        else {
	            System.out.println("SberBank option is not selected by default");
	        }

	        Thread.sleep(2000);
	        wait = new WebDriverWait(driver, 15);
	        By goAheadPaymentButton = By.xpath("//div[@id='bx-soa-paysystem']/div[2]/div[4]/div/a[2]");
	        wait.until(ExpectedConditions.presenceOfElementLocated(goAheadPaymentButton));
	        driver.findElement(goAheadPaymentButton).click();
	}
	
	@And("^User fills in delivery form - writes down contact information$")
	public void user_fills_in_delivery_form_writes_down_contact_information() throws InterruptedException {
		
		wait = new WebDriverWait(driver, 15);
		By fio = By.id("soa-property-36");
        wait.until(ExpectedConditions.elementToBeClickable(fio));
        driver.findElement(fio).sendKeys("Goldenberg");
        By email = By.id("soa-property-37");
        driver.findElement(email).sendKeys("sony@bk.com");
        By phone = By.id("soa-property-3");
        driver.findElement(phone).sendKeys("4544567654");
        By address = By.id("soa-property-23");
        driver.findElement(address).sendKeys("Sovetskaya");
        By house = By.id("soa-property-24");
        driver.findElement(house).sendKeys("45");
        By date = By.id("soa-property-30");
        driver.findElement(date).sendKeys("24.06.2021");
		
	}
	
	@Then("^User submits payment of the order$")
	public void user_submits_payment_of_the_order() {
		By order = By.xpath("//div[@id='bx-soa-orderSave']/a");
        driver.findElement(order).click();
	}
	
	@Then("^Verification - the order succesfully created$")
	public void verification_the_order_succesfully_created() {
		By successfulText = By.xpath("//table/tbody/tr[1]");
		String successText = driver.findElement(successfulText).getText();
		if (successText.contains("успешно создан")) {
			System.out.println("Succesfully created");
		}
		else {
			System.out.println("Something wrong");
		}
	}
	
}
