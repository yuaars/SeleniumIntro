import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helper {

    public static void check(WebElement checkbox){
        setCheckboxTo(checkbox,true);
        }

    public static void uncheck(WebElement checkbox){
        setCheckboxTo(checkbox,false);
        }

    public static boolean isAlertPresent(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            return true;
        }catch (NoAlertPresentException ex){
            return false;
        }
    }

    private static void setCheckboxTo(WebElement checkbox,boolean value){
        if(checkbox.isSelected() != value){
            checkbox.click();
        }
    }

}
