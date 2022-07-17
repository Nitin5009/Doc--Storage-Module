package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WaitStatement {

    WebDriver driver;
    public static Logger logger;
    public WaitStatement(WebDriver driver){
        this.driver=driver;
        logger = Logger.getLogger("");
    }

    public WebElement findElementVisibility(final By by,int time) {

        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            return null;
        }
    }

    public WebElement findElementClickable(final By by,int time) {

        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            return null;
        }
    }
    public WebElement findElementPresence(final By by,int time) {

        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            return null;
        }

    }

    public WebElement findElementsVisibility(final By by) {

        List<WebElement> webElements = driver.findElements(by);
        for (WebElement ele : webElements) {
            try {
                if (ele.isDisplayed()) {
                    return ele;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public WebElement waitForVisibilityOfElement(final By by,int time) {

        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            return wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean findElementsVisibilityByClick(final By by) {

        List<WebElement> webElements = driver.findElements(by);
        for(WebElement ele : webElements){
            try {
                if(ele.isDisplayed()){
                    ele.click();
                    return false;
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    public void waitForElementInVisibility(final By by, int time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            WebElement ele = findElementsVisibility(by);
            wait.until(ExpectedConditions.invisibilityOf(ele));
        } catch (Exception e) {
        }
    }
}
