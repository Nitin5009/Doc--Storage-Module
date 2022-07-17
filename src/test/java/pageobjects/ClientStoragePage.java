package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebBasePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static reporting.ComplexReportFactory.getTest;

public class ClientStoragePage extends WebBasePage {
    WebDriver driver;
    UserStoragePage userStorage;
    double sumInTable;
    String clientNameToSearch;
    String consumedStorageToSearch;

    public ClientStoragePage(WebDriver driver) {
        super(driver, "Client Storage page");
        this.driver = driver;
        this.userStorage = new UserStoragePage(driver);
    }



    public void openClientStoragePage() {
        userStorage.clickFullMenuDropDown();
        userStorage.clickDocStorageOption();
        WebElement element = findElementVisibility(By.xpath("//ul[@data-p-name='Doc. Storage']//a[text()='Manage Client Storage']"), 20);
        if (element != null) {
            click(By.xpath("//ul[@data-p-name='Doc. Storage']//a[text()='Manage Client Storage']"), "Manage Client Storage menu", 20);
        } else {
            openClientStoragePage();
        }
    }

    public void addStorageForDiffUser() {
        for (int i = 2; i <= 3; i++) {
            userStorage.clickEditIcon(i);
            userStorage.enterAllowedStorage(i);
            userStorage.clickSaveButton(i);
            handleStorageAddSuccess();
        }
    }

    public void handleStorageAddSuccess() {
        String successMessage = getText(By.xpath("//div[@id='notifymessage']/div/span"), 30);
        if (successMessage.equalsIgnoreCase("Storage assigned to client successfully")) {
            click(By.cssSelector(".text-secondary"), "Close Popup", 20);
            waitForLoader(20);
        } else {
            getTest().log(LogStatus.FAIL, "Storage assigned success message is not displayed");
            logger.info("Storage assigned success message is not displayed");
            takeScreenshot("StorageAssignSuccess");
        }
    }
    public void expandAndCollapse()
    {
        click(By.xpath("//a[@onclick='AddExpandCollapse(this)']"), "Expand and Collpase" ,15);
    }

    public void verifyTotalAllowedCount() {
        double countInList = 0;
        List<WebElement> listOFUser = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[6]/input"), 30);
        for (WebElement ele : listOFUser) {
            double count = Integer.parseInt(ele.getAttribute("value"));
            countInList = countInList + count;
        }
        String totalInTable = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[@id='trlTotal']//td//span[@id='spnTotalAllowed']"), 30).replaceAll(",","");
        sumInTable = Double.parseDouble(totalInTable);
        if (sumInTable == countInList) {
            getTest().log(LogStatus.PASS, "The Allotted counts of each client " + countInList + " is equal to the total count in the table - " + sumInTable);
            logger.info("The Allotted counts of each client " + countInList + " is equal to the total count in the table - " + sumInTable);
        } else {
            getTest().log(LogStatus.FAIL, "The Allotted counts of each client is not equal to the total count in the table");
            logger.info("The Allotted counts of each client is not equal to the total count in the table");
            takeScreenshot("UserTableCount");
        }
    }

    public void verifyAssignerToClientCount() {
        String countInHeader = getText(By.xpath("//span[text()='Assigned To Client']//parent::h3/span[@class]"), 30).replaceAll(",","");
        double countInTitle = Double.parseDouble(countInHeader);
        if (sumInTable == countInTitle) {
            getTest().log(LogStatus.PASS, "The sum of allotted storage displayed in the table " + sumInTable + " is equal to the value displayed along with the  \"Assigned To Client\" header - " + countInTitle);
            logger.info("The sum of allotted storage displayed in the table " + sumInTable + " is equal to the value displayed along with the  \"Assigned To Client\" header - " + countInTitle);
        } else {
            getTest().log(LogStatus.PASS, "The sum of allotted storage displayed in the table " + sumInTable + " is not equal to the value displayed along with the  \"Assigned To Client\" header - " + countInTitle);
            logger.info("The sum of allotted storage displayed in the table " + sumInTable + " is not equal to the value displayed along with the  \"Assigned To Client\" header - " + countInTitle);
            takeScreenshot("TableHeaderCount");
        }
    }

    public void expandClientNameSearch() {
        click(By.xpath("//span[@id='SearchingClientHeading']"), "Client name search", 15);
    }

    public void getClientNameToSearch() {
        clientNameToSearch = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[2]/span"), 30).trim();
    }

    public void enterClientInSearchBox() {
        enter(By.id("User"), clientNameToSearch, "Client Name search box", 15);
    }


    public void verifySearchedClientName() {
        List<String> expectedClientNameList = new ArrayList<>();
        int iteration = 0;
        List<WebElement> searchedClientNameLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td//input[contains(@name,'UserName')]//parent::span"), 30);
        if (searchedClientNameLocator.size() != 0) {
            for (WebElement clientNameLocator : searchedClientNameLocator) {
                if (clientNameLocator.isDisplayed()) {
                    expectedClientNameList.add(clientNameLocator.getText().trim());
                }
            }
            for (String expectedClientName : expectedClientNameList) {
                iteration++;
                if (!expectedClientName.equalsIgnoreCase(clientNameToSearch)) {
                    getTest().log(LogStatus.FAIL, expectedClientName + " is displayed when " + clientNameToSearch + " client name is searched");
                    logger.info(expectedClientName + " is displayed when " + clientNameToSearch + " client name is searched");
                    takeScreenshot("DepartmentNameSearch");
                } else if (expectedClientNameList.size() == iteration) {
                    getTest().log(LogStatus.PASS, "Selected client name " + clientNameToSearch + " is displayed as expected when search button is clicked");
                    logger.info("Selected client name " + clientNameToSearch + " is displayed as expected when search button is clicked");
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "Searched result for " + clientNameToSearch + " is not displayed");
            logger.info("Searched result for " + clientNameToSearch + " is not displayed");
            takeScreenshot("ClientNameSearch");
        }
    }

    public void expandConsumedStorageField() {
        click(By.xpath("//span[@id='SearchingConsumedHeading']"), "Consumed Storage field", 20);
    }

    public void getConsumedStorageFromTable() {
        consumedStorageToSearch = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[5]/span"), 30).trim();
    }

    public void enterConsumedStorageInSearchBox() {
        enter(By.xpath("//input[@id='txtConsumedStorage']"), consumedStorageToSearch, "Consumed storage search box", 20);
    }

    public void verifySearchedConsumedStorage() {
        List<String> expectedConsumedStorageList = new ArrayList<>();
        int iteration = 0;
        List<WebElement> searchedConsumedStorage = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td//input[contains(@name,'UsedStorage')]//parent::td/span"), 30);
        if (searchedConsumedStorage.size() != 0) {
            for (WebElement consumedStorage : searchedConsumedStorage) {
                if (consumedStorage.isDisplayed()) {
                    expectedConsumedStorageList.add(consumedStorage.getText().trim());
                }
            }
            for (String expectedConsumedStorage : expectedConsumedStorageList) {
                iteration++;
                if (!expectedConsumedStorage.equalsIgnoreCase(consumedStorageToSearch)) {
                    getTest().log(LogStatus.FAIL, expectedConsumedStorage + " is displayed when " + consumedStorageToSearch + " consumed storage is searched");
                    logger.info(expectedConsumedStorage + " is displayed when " + consumedStorageToSearch + " consumed storage is searched");
                    takeScreenshot("ConsumedStorageSearch");
                } else if (expectedConsumedStorageList.size() == iteration) {
                    getTest().log(LogStatus.PASS, "Search result for " + consumedStorageToSearch + " consumed storage is displayed as expected");
                    logger.info("Search result for " + consumedStorageToSearch + " consumed storage is displayed as expected");
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "Search result for " + consumedStorageToSearch + " consumed storage is not displayed");
            logger.info("Search result for " + consumedStorageToSearch + " consumed storage is not displayed");
            takeScreenshot("ConsumedStorageSearch");
        }
    }

    public void clientNameAscending() {
        clientNameSorting("ascending");
    }

    public void clientNameDescending() {
        clientNameSorting("descending");
    }

    public void clientNameSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#USER_NAME"), "User Name", 20);

        List<WebElement> clientNameLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td//input[contains(@name,'UserName')]//parent::span"), 30);
        for (WebElement clientName : clientNameLocators) {
            actualSortedArray.add(clientName.getText().trim());
        }

        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "ClientName");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "ClientName");
        }
    }

    public void emailAscending() {
        emailSorting("ascending");
    }

    public void emailDescending() {
        emailSorting("descending");
    }

    public void emailSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#EMAIL_ID"), "Email", 20);

        List<WebElement> designationLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td/input[contains(@name,'Email')]//parent::td"), 30);
        for (WebElement designation : designationLocators) {
            actualSortedArray.add(designation.getText().trim());
        }
        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "Email");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "Email");
        }
    }

    public void statusAscending() {
        statusSorting("ascending");
    }

    public void statusDescending() {
        statusSorting("descending");
    }

    public void statusSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#STATUS_NAME"), "Status header", 20);

        List<WebElement> statusLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td/input[contains(@name,'Status')]//parent::td/span"), 30);
        for (WebElement status : statusLocators) {
            actualSortedArray.add(status.getText().trim());
        }
        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "Status");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "Status");
        }
    }

    public void consumedStorageAscending() {
        consumedStorageSorting("ascending");
    }

    public void consumedStorageDescending() {
        consumedStorageSorting("descending");
    }

    public void consumedStorageSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#USED_STORAGE"), "Consumed Storage header", 20);

        List<WebElement> consumedStorageLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td/input[contains(@name,'UsedStorage')]//parent::td/span"), 30);
        for (WebElement consumedStorage : consumedStorageLocator) {
            actualSortedArray.add(consumedStorage.getText().trim());
        }
        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "Consumed Storage");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "Consumed Storage");
        }
    }

    public void allowedStorageAscending() {
        allowedStorageSorting("ascending");
    }

    public void allowedStorageDescending() {
        allowedStorageSorting("descending");
    }

    public void allowedStorageSorting(String order) {
        List<Double> actualSortedArray = new ArrayList<>();
        List<Double> expectedSortedArray;
        click(By.cssSelector("#ALLOWED_STORAGE"), "Allowed Storage header", 20);

        List<WebElement> consumedStorageLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[6]/input"), 30);
        for (WebElement consumedStorage : consumedStorageLocator) {
            actualSortedArray.add(Double.parseDouble(consumedStorage.getAttribute("value").trim()));
        }
        expectedSortedArray = actualSortedArray;
        Collections.sort(expectedSortedArray);
        if (order.equalsIgnoreCase("descending")) {
            Collections.reverse(expectedSortedArray);
        }
        if (expectedSortedArray.equals(actualSortedArray)) {
            getTest().log(LogStatus.PASS, "Allowed Storage are sorted in " + order + " Order when click the Allowed Storage header for one time");
            logger.info("Allowed Storage are sorted in " + order + " Order when click the Allowed Storage header for one time");
        } else {
            getTest().log(LogStatus.FAIL, "Allowed Storage are not sorted in " + order + " Order when click the Allowed Storage header for one time");
            logger.info("Allowed Storage are not sorted in " + order + " Order when click the Allowed Storage header for one time");
            takeScreenshot("AllowedStorage" + order);
        }
    }

    public void verifyAscendingOrder(List<String> actualSortedArray, String fieldName) {
        List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
        expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
        if (expectedSortedList.equals(actualSortedArray)) {
            getTest().log(LogStatus.PASS, fieldName + "s are sorted in ascending Order when click the " + fieldName + " header for one time");
            logger.info(fieldName + "s are sorted in ascending Order when click the " + fieldName + " header for one time");
        } else {
            getTest().log(LogStatus.FAIL, fieldName + "s are not sorted in ascending Order when click the " + fieldName + " header for one time");
            logger.info(fieldName + "s are not sorted in ascending Order when click the " + fieldName + " header for one time");
            takeScreenshot(fieldName + "Ascending");
        }
    }

    public void verifyDescendingOrder(List<String> actualSortedArray, String fieldName) {
        List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
        expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
        Collections.reverse(expectedSortedList);
        if (expectedSortedList.equals(actualSortedArray)) {
            getTest().log(LogStatus.PASS, fieldName + "s are sorted in descending Order when click the " + fieldName + " header for two times");
            logger.info(fieldName + "s are sorted in descending Order when click the " + fieldName + " header for two times");
        } else {
            getTest().log(LogStatus.FAIL, fieldName + "s are not sorted in descending Order when click the " + fieldName + " header for two times");
            logger.info(fieldName + "s are not sorted in descending Order when click the " + fieldName + " header for two times");
            takeScreenshot(fieldName + "Descending");
        }
    }
}
