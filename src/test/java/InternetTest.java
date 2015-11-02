import jdk.nashorn.internal.ir.annotations.Ignore;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
    //не доделанный (хз как) :(
    public void elementAppearedTest() throws InterruptedException {
        driver.findElement(By.linkText("Dynamic Loading")).click();
        driver.findElement(By.partialLinkText("Example 2")).click();
        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        WebElement finishBlock = driver.findElement(By.xpath(".//*[@id='finish']/h4"));
        //WebElement startButton = driver.findElement(By.id("start"));
        Assert.assertTrue(startButton.isDisplayed(), "Start button didn't disappeared");
        startButton.click();
        Assert.assertFalse(startButton.isDisplayed(), "Start button is still there");


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

        Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is invisible");

        WebElement alertMessage = driver.findElement(By.id("flash"));
        wait.until(ExpectedConditions.visibilityOf(alertMessage));
        Assert.assertTrue(alertMessage.isDisplayed(), "You logged into a secure area!");

        //добавить проверку что есть алерт  You logged into a secure area!




    }
    //doesn't work
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

        Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is invisible");

        WebElement alertMessage = driver.findElement(By.id("flash"));
        wait.until(ExpectedConditions.visibilityOf(alertMessage));
        Assert.assertTrue(alertMessage.isDisplayed(), "You logged into a secure area!");

        //logout
        logoutButton.click();
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://the-internet.herokuapp.com/login";

        Assert.assertEquals(currentURL, expectedURL);

    }

    @Test
    //validation test
    public void testAuthenticationFailureWhenProvidingBadCredentials(){
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

    @Test
    public void titleTyping() throws InterruptedException {
        String pageTitle = driver.getTitle();
        System.out.println(pageTitle);
        Thread.sleep(5000);
    }

    @Test
    public void dropdownTest(){

        driver.findElement(By.linkText("Dropdown")).click();
        WebElement select = driver.findElement(By.id("dropdown"));

        //специальный класс для дродаунов
        Select dropdown = new Select(select);

        // options хранит все присутствующие элементы в дропдауне
        List<WebElement> options =  dropdown.getOptions();

        Assert.assertEquals(options.size(),3);
        Assert.assertFalse(dropdown.isMultiple());
        //первый текст по умолчанию
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),"Please select an option");

        //проверяем первый(который по умолчанию) опшн что он disabled
        Assert.assertEquals(options.get(0).getText(),"Please select an option");
        Assert.assertFalse(options.get(0).isEnabled());

        //проверяем второй
        Assert.assertEquals(options.get(1).getText(),"Option 1");

        //проверяем третий
        Assert.assertEquals(options.get(2).getText(),"Option 2");

        dropdown.selectByValue("1");
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),"Option 1");

    }

    @Test
    public void alertTest() {
        driver.findElement(By.linkText("JavaScript Alerts")).click();

        WebElement button = driver.findElement(By.cssSelector("button[onclick='jsAlert()']"));
        button.click();

        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Alert");
        alert.accept();

        Assert.assertFalse(Helper.isAlertPresent(driver),"Alert is present");
        Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You successfuly clicked an alert");


    }

    @Test
    public void confirmTest(){
        driver.findElement(By.linkText("JavaScript Alerts")).click();

        WebElement button = driver.findElement(By.cssSelector("button[onclick='jsConfirm()']"));
        button.click();

        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Confirm");
        alert.accept();

        Assert.assertFalse(Helper.isAlertPresent(driver),"Alert is present");

        Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You clicked: Ok");
    }

    @Test
    public void promptTest() throws InterruptedException {
        driver.findElement(By.linkText("JavaScript Alerts")).click();


        WebElement button = driver.findElement(By.cssSelector("button[onclick='jsPrompt()']"));
        Thread.sleep(2000);
        button.click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS prompt");
        String enteredText = "Hello prompt!!!";
        alert.sendKeys(enteredText);
        Assert.assertEquals(driver.findElement(By.id("result")).getText(),enteredText);


    }

    @Test
    public void tabsTest(){

        /* специальный идентификатор handles */
        driver.findElement(By.linkText("Multiple Windows")).click();


        WebElement clickHere = driver.findElement(By.cssSelector("#content a"));
        clickHere.click();

        List<String> handles = new ArrayList<String>(driver.getWindowHandles());
        Assert.assertEquals(handles.size(),2 );

        /* нужно переключиться на другое окно вручную */
        driver.switchTo().window(handles.get(1));

        Assert.assertEquals(driver.getCurrentUrl(),"http://the-internet.herokuapp.com/windows/new");
        Assert.assertEquals(driver.getTitle(),"New Window");

        WebElement heading = driver.findElement(By.cssSelector("div.example h3"));
        Assert.assertEquals(heading.getText(),"New Window");
        driver.close();

        Assert.assertEquals(driver.getWindowHandles().size(),1);
        driver.switchTo().window(handles.get(0));
        Assert.assertEquals(driver.getCurrentUrl(),"http://the-internet.herokuapp.com/windows");

    }

    @Test
    public void frameTest(){
        driver.findElement(By.linkText("Frames")).click();
        driver.findElement(By.linkText("Nested Frames")).click();

        //bottom
        driver.switchTo().frame("frame-bottom");
        Assert.assertEquals(driver.findElement(By.tagName("body")).getText().trim(),"BOTTOM");

        //посмотреть разметку
        //left
        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        Assert.assertEquals(driver.findElement(By.tagName("body")).getText().trim(),"LEFT");

        //middle
        driver.switchTo().parentFrame();
        //driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-middle");
        Assert.assertEquals(driver.findElement(By.tagName("body")).getText().trim(),"MIDDLE");

        //right
        driver.switchTo().parentFrame();
        //driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-right");
        Assert.assertEquals(driver.findElement(By.tagName("body")).getText().trim(),"RIGHT");
    }

    @Test
    public void iFrameTest() {
        driver.findElement(By.linkText("Frames")).click();
        driver.findElement(By.linkText("iFrame")).click();

        //JS code goes here
        ((JavascriptExecutor)driver).executeScript
                ("document.getElementsByClassName('large-4')[0].setAttribute('style', 'display: none');");
        Assert.assertFalse(driver.findElement(By.cssSelector(".large-4")).isDisplayed());


        driver.manage().window().setSize(new Dimension(600,600));
        WebElement listButton = driver.findElement(By.cssSelector("#mceu_9>button"));
        listButton.click();

        driver.switchTo().frame("mce_0_ifr");



        Actions actions = new Actions(driver);
        actions.sendKeys("Text");


    }
}

