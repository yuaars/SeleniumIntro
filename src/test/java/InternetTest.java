import jdk.nashorn.internal.ir.annotations.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
        // �� ���� �����
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
    //�� ���������� (�� ���) :(
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

        //�������� �������� ��� ���� �����  You logged into a secure area!




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

        //����������� ����� ��� ���������
        Select dropdown = new Select(select);

        // options ������ ��� �������������� �������� � ���������
        List<WebElement> options =  dropdown.getOptions();

        Assert.assertEquals(options.size(),3);
        Assert.assertFalse(dropdown.isMultiple());
        //������ ����� �� ���������
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),"Please select an option");

        //��������� ������(������� �� ���������) ���� ��� �� disabled
        Assert.assertEquals(options.get(0).getText(),"Please select an option");
        Assert.assertFalse(options.get(0).isEnabled());

        //��������� ������
        Assert.assertEquals(options.get(1).getText(),"Option 1");

        //��������� ������
        Assert.assertEquals(options.get(2).getText(),"Option 2");

        dropdown.selectByValue("1");
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),"Option 1");

    }
}
