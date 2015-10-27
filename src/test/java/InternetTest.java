import jdk.nashorn.internal.ir.annotations.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InternetTest {

    private WebDriver driver;
    private final static String BASE_URL = "http://the-internet.herokuapp.com";

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        // на весь экран
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30,TimeUnit.SECONDS);
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

    @Ignore
    @Test
    public void elementGotVisiblerTest(){

        driver.findElement(By.linkText("Dynamic Loading")).click();
        driver.findElement(By.partialLinkText("Example 1")).click();
        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        WebElement finishBlock = driver.findElement(By.id("finish"));
        startButton.click();
        Assert.assertFalse(startButton.isDisplayed(),"Start button didn't disappeared");
        WebDriverWait wait = new WebDriverWait(driver,12);
        wait.until(ExpectedConditions.visibilityOf(finishBlock));
        //Thread.sleep(10000);
        Assert.assertTrue(finishBlock.isDisplayed(),"Finish block is invisible");
        Assert.assertEquals(finishBlock.getText(), "Hello World!");
    }

    @Ignore
    @Test
    //не доделанный
    public void elementAppearedTest(){
        driver.findElement(By.linkText("Dynamic Loading")).click();
        driver.findElement(By.partialLinkText("Example 2")).click();
        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        WebElement finishBlock = driver.findElement(By.id("finish"));
        startButton.click();
        Assert.assertFalse(startButton.isDisplayed(), "Start button didn't disappeared");
        WebDriverWait wait = new WebDriverWait(driver,12);
        wait.until(ExpectedConditions.visibilityOf(finishBlock));
        //Thread.sleep(10000);
        Assert.assertTrue(finishBlock.isDisplayed(),"Finish block is invisible");
        Assert.assertEquals(finishBlock.getText(), "Hello World!");

    }
    @Test
    public void loginTest() {
        driver.findElement(By.linkText("Form Authentication")).click();
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        WebElement login = driver.findElement(By.cssSelector(".radius"));
        login.click();

        WebDriverWait wait = new WebDriverWait(driver,12);

        WebElement logoutButton = driver.findElement(By.cssSelector(".icon-2x.icon-signout"));
        wait.until(ExpectedConditions.visibilityOf(logoutButton));

        Assert.assertTrue(logoutButton.isDisplayed(),"Logout button is invisible");

        WebElement alertMessage = driver.findElement(By.id("flash"));
        wait.until(ExpectedConditions.visibilityOf(alertMessage));
        Assert.assertTrue(alertMessage.isDisplayed(), "You logged into a secure area!");

        //добавить проверку что есть алерт  You logged into a secure area!




    }
    @Test
    public void checkboxTest(){
        driver.findElement(By.linkText("Checkboxes")).click();
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox]"));

        Helper.check(checkboxes.get(0));
        Helper.uncheck(checkboxes.get(1));


        Assert.assertTrue(checkboxes.get(0).isSelected());
        Assert.assertFalse(checkboxes.get(1).isSelected());
    }

    @Test
    public void logOutTest(){
        //login
        driver.findElement(By.linkText("Form Authentication")).click();
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        WebElement login = driver.findElement(By.cssSelector(".radius"));
        login.click();

        WebDriverWait wait = new WebDriverWait(driver,12);

        WebElement logoutButton = driver.findElement(By.cssSelector(".icon-2x.icon-signout"));
        wait.until(ExpectedConditions.visibilityOf(logoutButton));

        Assert.assertTrue(logoutButton.isDisplayed(),"Logout button is invisible");

        WebElement alertMessage = driver.findElement(By.id("flash"));
        wait.until(ExpectedConditions.visibilityOf(alertMessage));
        Assert.assertTrue(alertMessage.isDisplayed(), "You logged into a secure area!");

        //logout
        logoutButton.click();
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://the-internet.herokuapp.com/login";

        Assert.assertEquals(currentURL,expectedURL);

    }

    @Test
    public void validationTest(){
        driver.findElement(By.linkText("Form Authentication")).click();
        String wrongLogin = "wrong_tomsmith";
        String wrongPassword = "wrong_SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(wrongLogin);
        driver.findElement(By.id("password")).sendKeys(wrongPassword);
        WebElement login = driver.findElement(By.cssSelector(".radius"));
        login.click();

        WebElement alertMessage = driver.findElement(By.id("flash"));
        WebDriverWait wait = new WebDriverWait(driver,12);
        wait.until(ExpectedConditions.visibilityOf(alertMessage));
        Assert.assertTrue(alertMessage.isDisplayed(), "Your username is invalid!");


    }
}
