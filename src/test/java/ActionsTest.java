import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ActionsTest {

    private WebDriver driver;
    private final static String BASE_URL = "http://the-internet.herokuapp.com";

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        // на весь экран
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30,TimeUnit.SECONDS);
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void teardown(){

        driver.quit();
    }

    @Test
    public void sendKeysTest() throws InterruptedException {

        driver.findElement(By.linkText("Key Presses")).click();
        Actions actions = new Actions(driver);

        actions.contextClick();

        for (int i = 80; i < 90; i++) {

            //переводим символы в строку
            String enteredValue = String.valueOf((char)i);
            actions.sendKeys(enteredValue).perform();
            //actions.sendKeys(Keys.CONTROL + "A").perform();
            Thread.sleep(1000);
            WebElement displayed = driver.findElement(By.id("result"));
            Assert.assertEquals(displayed.getText(),"You entered: " + enteredValue);
            Thread.sleep(500);
        }
    }

    //only for Firefox
    @Test
    public void contextMenuTest() throws InterruptedException {

        driver.findElement(By.linkText("Context Menu")).click();
        WebElement contextStuff = driver.findElement(By.id("hot-spot"));
        Assert.assertTrue(contextStuff.isEnabled());
        Actions actions = new Actions(driver);

        actions
                .contextClick(contextStuff)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER).perform();

        Thread.sleep(3000);

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(),"You selected a context menu");
        alert.accept();
        Assert.assertFalse(Helper.isAlertPresent(driver),"Alert is present");

    }

    @Test
    public void hoverTest(){

        driver.findElement(By.linkText("Hovers")).click();
        List<WebElement> users = driver.findElements(By.cssSelector(".figure"));
        Actions action = new Actions(driver);
        int counter = 1;
        for (WebElement user : users){
            action.moveToElement(user).perform();
            Assert.assertTrue(user.findElement(By.tagName("h5")).isDisplayed());
            Assert.assertEquals(user.findElement(By.tagName("h5")).getText(),"name: user" + counter);
            counter++;
        }
    }

    @Test
    public void dragAndDropTest() throws InterruptedException {
        driver.findElement(By.linkText("Drag and Drop")).click();

        WebElement source = driver.findElement(By.id("column-a"));
        WebElement target = driver.findElement(By.id("column-b"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source,target).perform();
        Thread.sleep(5000);
        Assert.assertEquals(source.findElement(By.tagName("header")).getText(),"B");
        Assert.assertEquals(target.findElement(By.tagName("header")).getText(),"A");
    }



}
