import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class Main {

    public static void main (String [] args) throws InterruptedException {

     WebDriver driver = new ChromeDriver();
        driver.get("http://www.onliner.by/");
        Thread.sleep(10000);
        WebElement baraholka = driver.findElement(By.linkText("Барахолка")); //найти барахолку по xpath чтоб без русского языка
        By elementLocator = By.id("container");
        //driver.findElement(elementLocator);
        baraholka.click();
        Thread.sleep(3000);
        driver.quit();

    }
}
