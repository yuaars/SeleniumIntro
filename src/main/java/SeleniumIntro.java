import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumIntro {

    public static void main (String []args){

        //WebDriver driver = new FirefoxDriver();
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.onliner.by/");

        WebElement baraholka = driver.findElement(By.xpath("//*[@href = 'http://baraholka.onliner.by/']"));
        //findElement(By.xpath("//*[@href='http://catalog.onliner.by/']"))
        baraholka.click();
        driver.quit();
    }
}
