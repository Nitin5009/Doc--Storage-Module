package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static reporting.ComplexReportFactory.getTest;

public class WebBasePage extends WaitStatement {

    private WebDriver driver;
    public static Logger logger;
    private String pageName;

    public WebBasePage(WebDriver driver, String pageName) {
        super(driver);
        this.driver = driver;
        this.pageName = pageName;
        logger = Logger.getLogger(pageName);
    }

    public void open(String url) {

        driver.get(url);
        getTest().log(LogStatus.PASS, "Url opened - " + url);
    }

    public List<WebElement> findMultipleElement(By by, int time) {
        List<WebElement> elements = new ArrayList<>();
        try {
            staticWait(2000);
            WebElement element = findElementVisibility(by, time);
            waitForVisibilityOfElement(by, time);
            elements = driver.findElements(by);
            return elements;
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Element not found : " + by + ". Error occurred. " + e);
            logger.info("Element not found : " + by + ". Error occurred. " + e);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("" + e);
            return elements;
        }
    }

    public void waitForLoader(int time) {
        try {
            waitForElementInVisibility(By.cssSelector(".lds-ring"), time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enter(By by, String value, String name, int time) {
        try {
            WebElement element = findElementVisibility(by, time);
            staticWait(200);
            element.clear();
            element.sendKeys(value);
            getTest().log(LogStatus.PASS, name + " entered with value - " + value);
            logger.info(name + " entered with value - " + value);
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, pageName + name + " not entered with value - " + value + ", error exist - " + e);
            logger.info(name + " not entered with value - " + value + ", error exist - " + e);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("" + e);
        }
    }

    public void click(By by, String name, int time) {

        WebElement element = findElementVisibility(by, time);
        staticWait(200);
        if (element != null) {
            try {
                element.click();
                getTest().log(LogStatus.PASS, name + " clicked");
                logger.info(name + " clicked ");
            } catch (Exception e) {
                getTest().log(LogStatus.FAIL, pageName + name + " not clicked ");
                logger.info(name + " not clicked");
                takeScreenshot(new Object() {
                }.getClass().getEnclosingMethod().getName());
                Assert.fail(name + " -  element not clickable");
            }
        } else {
            getTest().log(LogStatus.FAIL, pageName + name + " not clicked ");
            logger.info(name + " not clicked");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail(name + " -  element not present");
        }

    }

    public void selectValueWithIndex(By by, int value, String name, int time) {
        boolean notStaleElement = false;
        for (int i = 0; i <= 30; i++) {
            try {
                WebElement element = findElementVisibility(by, time);
                Select se = new Select(element);
                se.selectByIndex(value);
                notStaleElement = true;
                break;
            } catch (Exception e) {

            }
            staticWait(200);
        }
        if (notStaleElement) {
            getTest().log(LogStatus.PASS, name + " selected with index - " + value);
            logger.info(name + " selected with index - " + value);
        } else {
            getTest().log(LogStatus.FAIL, name + " not selected with index - " + value);
            logger.info(name + " not selected with index - " + value);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail(name + " not selected with " + value);
        }
    }

    public void selectValueWithValue(By by, String value, String name, int time) {
        boolean notStaleElement = false;
        for (int i = 0; i <= 30; i++) {
            try {
                WebElement element = findElementVisibility(by, time);
                Select se = new Select(element);
                se.selectByValue(value);
                notStaleElement = true;
                break;
            } catch (Exception e) {

            }
            staticWait(200);
        }
        if (notStaleElement) {
            getTest().log(LogStatus.PASS, name + " selected with value - " + value);
            logger.info(name + " selected with value - " + value);
        } else {
            getTest().log(LogStatus.FAIL, name + " not selected with value - " + value);
            logger.info(name + " not selected with value - " + value);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail(name + " not selected with " + value);
        }
    }

    public void selectValueWithText(By by, String value, String name, int time) {
        staticWait(200);
        boolean notStaleElement = false;
        for (int i = 0; i <= 30; i++) {
            try {
                WebElement element = findElementVisibility(by, time);
                Select se = new Select(element);
                se.selectByVisibleText(value);
                notStaleElement = true;
                break;
            } catch (Exception e) {

            }
            staticWait(200);
        }
        if (notStaleElement) {
            getTest().log(LogStatus.PASS, name + " selected with text - " + value);
            logger.info(name + " selected with text - " + value);
        } else {
            getTest().log(LogStatus.FAIL, name + " not selected with text - " + value);
            logger.info(name + " not selected with text - " + value);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail(name + " not selected with " + value);
        }
    }

    public void scrollDown() {
        try {
            staticWait(2000);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            getTest().log(LogStatus.PASS, "Page scroll down");
            logger.info("Page scroll down");
            staticWait(2000);
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Error Occurred " + e);
            logger.info("Error Occurred " + e);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("" + e);
        }
    }

    public void staticWait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void scrollToWebelement(By by, String name) {
        try {
            staticWait(500);
            WebElement element = findElementPresence(by, 20);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView();", element);
            getTest().log(LogStatus.PASS, "Scroll to this element - " + name);
            logger.info("Scroll to this element - " + name);
            staticWait(500);
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Error occurred. " + e);
            logger.info("Error occurred. " + e);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("" + e);
        }
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void clickByJavascript(By by, String name, int time) {

        try {
            staticWait(2000);
            WebElement element = findElementPresence(by, time);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            getTest().log(LogStatus.PASS, name + " click by JS");
            logger.info(name + " click by JS");
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Error Occurred. " + e);
            logger.info("Error Occurred. " + e);
            takeScreenshot("NotClickByJS");
            Assert.fail("" + e);
        }

    }


    public String getText(By by, int time) {
        try {
            WebElement ele = findElementVisibility(by, time);
            String getText = ele.getText();
            logger.info(" Text displayed is  - " + getText);
            return getText;
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Error Occured. " + e);
            logger.info("Error Occured. " + e);
            Assert.fail("" + e);
            return null;
        }
    }

    public String getAtribute(By by, String tag, int time) {
        try {
            WebElement ele = findElementVisibility(by, time);
            String getText;
            getText = ele.getAttribute(tag);
            logger.info(" get attribute value is  - " + getText);
            return getText;
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Error Occurred. " + e);
            logger.info("Error Occurred. " + e);
            Assert.fail("" + e);
            return null;
        }
    }

    public void waitForLoad(int timeoutSec) {
        try {
            new WebDriverWait(driver, timeoutSec).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void scrollUpDown(String scroll) {
        Actions actions = new Actions(driver);
        try {
            if (scroll.equalsIgnoreCase("down")) {
                actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
                actions.keyUp(Keys.CONTROL).perform();
            } else if (scroll.equalsIgnoreCase("up")) {
                actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
                actions.keyUp(Keys.CONTROL).perform();
            }
        } catch (Exception e) {
            e.printStackTrace();
            takeScreenshot("ScrollUpDown");
            Assert.fail("" + e);
        }
    }

    public boolean switchToTab(int tabPosition) {
        boolean tabFound = false;
        Set<String> allWindowHandles = driver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<String>(allWindowHandles);
        if (tabs.size() > 0) {
            try {
                driver.switchTo().window(tabs.get(tabPosition));
            } catch (Exception e) {
                getTest().log(LogStatus.FAIL, "Error in opening new tab");
                takeScreenshot("ErrorInOpenWindow");
                return false;
            }
            tabFound = true;
        }
        if (!tabFound) {
            getTest().log(LogStatus.FAIL, " There are no new tabs");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
        }

        return tabFound;

    }

    public void closeCurrentTab(int currentTabIndex) {
        try {
            Set<String> allWindowHandles = driver.getWindowHandles();
            ArrayList<String> tabs = new ArrayList<String>(allWindowHandles);
            driver.switchTo().window(tabs.get(currentTabIndex));
            driver.close();
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Not able to close current tab");
            logger.info("Not able to close  current tab");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("Not able to close current tab");
        }
    }

    public void switchToParentTab() {
        try {
            Set<String> allWindowHandles = driver.getWindowHandles();
            ArrayList<String> tabs = new ArrayList<String>(allWindowHandles);
            driver.switchTo().window(tabs.get(0));
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Not able to switch to parent tab");
            logger.info("Not able to switch to parent tab");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("Not able to switch to parent tab");
        }
    }

    public void takeScreenshot(String name) {
        String imagePath = System.getProperty("user.dir") + "\\reports\\" + name + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            org.apache.commons.io.FileUtils.copyFile(scrFile, new File(imagePath + ".png"));
            System.out.println(imagePath + ".png");
        } catch (Exception e) {
            Assert.fail("Error while taking screenshot - " + e);
        }
        getTest().log(LogStatus.INFO, getTest().addScreenCapture(imagePath + ".png"));
    }

    public int countNumberOfFilesInFolder(String path) {
        try {
            return Objects.requireNonNull(new File(path).list()).length;
        } catch (NullPointerException e) {
            getTest().log(LogStatus.FAIL, "File directory not found. " + path);
            logger.info("File directory not found. " + path);
            takeScreenshot("DirectoryNotFound");
            Assert.fail("" + e);
            return 0;
        }
    }

    public void waitTillNewFile(String path, int previousFileCount) {
        try {
            staticWait(5000);
            int timeCount = 1;
            for (timeCount = 1; timeCount < 80; timeCount++) {
                if (countNumberOfFilesInFolder(path) > previousFileCount) {
                    getTest().log(LogStatus.PASS, "File downloaded...");
                    break;
                }
                staticWait(500);
                System.out.println("countNumberOfFilesInFolder(path) - " + countNumberOfFilesInFolder(path) + " previousFileCount - " + previousFileCount);
                logger.info("countNumberOfFilesInFolder(path) - " + countNumberOfFilesInFolder(path) + " previousFileCount - " + previousFileCount);
            }
        } catch (Exception e) {
            takeScreenshot("WaitTillNewFile");
            Assert.fail("" + e);
        }

    }

    public void uploadDoc(By by, String value, String name, int time) {
        try {
            WebElement element = findElementPresence(by, time);
            staticWait(500);
            element.sendKeys(value);
            getTest().log(LogStatus.PASS, name + " uploaded with value - " + value);
            logger.info(name + " uploaded with value - " + value);
        } catch (Exception e) {
            getTest().log(LogStatus.FAIL, "Error occurred. " + e);
            logger.info("Error occurred. " + e);
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("" + e);
        }
    }

    public void clickMultipleTimes(By by, String name, int time) {

        WebElement element = findElementVisibility(by, time);
        staticWait(200);
        if (element != null) {
            if (multipleClick(element, time)) {
                getTest().log(LogStatus.PASS, name + " clicked");
                logger.info(name + " clicked ");
            } else {
                getTest().log(LogStatus.FAIL, pageName + name + " not clicked ");
                logger.info(name + " not clicked");
                takeScreenshot(new Object() {
                }.getClass().getEnclosingMethod().getName());
                Assert.fail(name + " -  element not clickable");
            }

        } else {
            getTest().log(LogStatus.FAIL, pageName + name + " not clicked ");
            logger.info(name + " not clicked");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail(name + " -  element not present");
        }

    }

    public boolean multipleClick(WebElement element, int count) {
        for (int cl = 1; cl <= count; cl++) {
            try {
                staticWait(300);
                element.click();
                return true;
            } catch (StaleElementReferenceException e) {

            }
        }
        return false;
    }

    public String getText(By by, int time, int staleCount) {

        boolean stateElement = false;
        String text = null;
        WebElement element = findElementVisibility(by, time);
        if (element != null) {
            for (int inc = 1; inc <= staleCount; inc++) {
                try {
                    text = element.getText();
                    getTest().log(LogStatus.PASS, "Text is displayed as " + text);
                    logger.info("Text is displayed as " + text);
                    stateElement = true;
                    break;
                } catch (StaleElementReferenceException e) {

                }
                staticWait(200);
            }
            if (!stateElement) {
                getTest().log(LogStatus.FAIL, "No text found");
                logger.info("No text found");
                takeScreenshot(new Object() {
                }.getClass().getEnclosingMethod().getName());
                Assert.fail("No text found");
            }

        } else {
            getTest().log(LogStatus.FAIL, "No text found");
            logger.info("No text found");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail("No text found");
        }
        return text;
    }

    public String getAtribute(By by, String tag, int time, int staleCount) {

        boolean stateElement = false;
        String text = null;
        WebElement element = findElementVisibility(by, time);
        if (element != null) {
            for (int inc = 1; inc <= staleCount; inc++) {
                try {
                    text = element.getAttribute(tag);
                    getTest().log(LogStatus.PASS, "Get attribute value is" + text);
                    logger.info("Get attribute value is" + text);
                    stateElement = true;
                    break;
                } catch (StaleElementReferenceException e) {

                }
                staticWait(200);
            }
            if (!stateElement) {
                getTest().log(LogStatus.FAIL, tag + " attribute not found");
                logger.info(tag + " attribute not found");
                takeScreenshot(new Object() {
                }.getClass().getEnclosingMethod().getName());
                Assert.fail(tag + " attribute not found");
            }

        } else {
            getTest().log(LogStatus.FAIL, tag + " attribute not found");
            logger.info(tag + " attribute not found");
            takeScreenshot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            Assert.fail(tag + " attribute not found");
        }
        return text;
    }
}


