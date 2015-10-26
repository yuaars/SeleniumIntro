import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SimpleTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        // на весь экран
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30,TimeUnit.SECONDS);

    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

    @Test(expectedExceptions = StaleElementReferenceException.class)
    public void refreshTest() throws InterruptedException {

        //arrange
        //driver.navigate().to("http://www.onliner.by/");
        //get потому что короче
        driver.get("http://www.onliner.by/");
        Assert.assertEquals("http://www.onliner.by/",driver.getCurrentUrl());
        WebElement element = driver.findElement(By.xpath(".//*[@id='container']/div/div[2]/header/nav/ul[2]/li[6]/a"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl,driver.getCurrentUrl());


       driver.navigate().refresh();

        //Assert

        element.click();
        Thread.sleep(3000);
        Assert.assertEquals("http://baraholka.onliner.by/",driver.getCurrentUrl());

    }

    @Test
    public void backTest(){

        driver.get("http://www.onliner.by/");
        String onliner = driver.getCurrentUrl();
        driver.findElement(By.xpath(".//*[@id='container']/div/div[2]/header/nav/ul[2]/li[6]/a")).click();

        //записываем текущий адрес
        //String baraholka = driver.getCurrentUrl();
        driver.navigate().back();
        Assert.assertEquals(onliner,driver.getCurrentUrl());

    }

    @Test
    public void forwardTest(){
        driver.get("http://baraholka.onliner.by/");
        //недвижимость
        driver.findElement(By.xpath(".//*[@id='minWidth']/div/div[3]/ul[1]/li[5]/a[1]")).click();
        String nedvizh = driver.getCurrentUrl();
        //обратно на барахолку
        driver.navigate().back();
        //вперед на недвижимость
        driver.navigate().forward();
        Assert.assertEquals(nedvizh,driver.getCurrentUrl());
    }
}



