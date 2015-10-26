import org.openqa.selenium.WebElement;

public class Helper {
    public static void check(WebElement checkbox){
        setCheckboxTo(checkbox,true);
        }

    public static void uncheck(WebElement checkbox){
        setCheckboxTo(checkbox,false);
        }

    private static void setCheckboxTo(WebElement checkbox,boolean value){
        if(checkbox.isSelected() != value){
            checkbox.click();
        }
    }

}
