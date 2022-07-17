package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebBasePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static reporting.ComplexReportFactory.getTest;

public class UserStoragePage extends WebBasePage {
    WebDriver driver;
    double sumInTable;
    String departmentToSearch;
    String designationToSearch;
    String userNameToSearch;
    String consumedStorageToSearch;

    public UserStoragePage(WebDriver driver) {
        super(driver, "User Storage page");
        this.driver = driver;
    }
    public void clickFullMenuDropDown()
    {
        findElementClickable(By.cssSelector("a#navbarDropdownPortfolio"), 20);
        click(By.cssSelector("a#navbarDropdownPortfolio"), "Full Menu", 20);
    }
    public void openUserStoragePage() {
        clickFullMenuDropDown();
        clickDocStorageOption();
        WebElement element = findElementVisibility(By.xpath("//ul[@data-p-name='Doc. Storage']//a[text()='User Storage']"), 20);
        if (element != null) {
            click(By.xpath("//ul[@data-p-name='Doc. Storage']//a[text()='User Storage']"), "User Storage menu", 20);
        } else {
            openUserStoragePage();
        }
    }

    public void clickDocStorageOption() {
        WebElement docStorageOption = findElementVisibility(By.xpath("//li[@class='menuitem']//a[text()='Doc. Storage']"), 15);
        if (docStorageOption != null) {
            click(By.xpath("//li[@class='menuitem']//a[text()='Doc. Storage']"), "Doc.Storage menu", 15);
        } else {
            driver.navigate().refresh();
            clickFullMenuDropDown();
            clickDocStorageOption();
        }
    }

    public void clickEditIcon(int rowNum) {
        findElementVisibility(By.xpath("//i[@class='fa fa-arrow-circle-left']"), 180);
        click(By.xpath("//table[@id='tblStorage']//tbody//tr[" + rowNum + "]//td[contains(@class,'single-action')]/a[@class='aEdit']"), "Edit icon of user " + rowNum, 25);
    }

    public void enterAllowedStorage(int rowNum) {
        Random r = new Random();
        int low = 1;
        int high = 999;
        int result = r.nextInt(high - low) + low;

        enter(By.xpath("//table[@id='tblStorage']//tbody//tr[" + rowNum + "]//td[contains(@class,'single-action')]/input"), "" + result, "Allowed storage for user " + rowNum, 20);
    }

    public void clickCancelButton() {
        click(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[contains(@class,'single-action')]/a[@class='aCancel']"), "Cancel button for user 1", 15);
    }

    public void verifyAllowedStorageDisabled() {
        WebElement element = findElementVisibility(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[contains(@class,'single-action')]/input"), 20);
        if (!element.isEnabled()) {
            getTest().log(LogStatus.PASS, "The Allowed storage field is disabled as expected when click on the cancel button");
            logger.info("The Allowed storage field is disabled as expected when click on the cancel button");
        } else {
            getTest().log(LogStatus.FAIL, "The Allowed field is enabled when click on the cancel button");
            logger.info("The Allowed field is enabled when click on the cancel button");
            takeScreenshot("AllowedStorageDisable");
        }
    }

    public void clickSaveButton(int rowNum) {
        click(By.xpath("//table[@id='tblStorage']//tbody//tr[" + rowNum + "]//td[contains(@class,'single-action')]/a[@class='aSave']"), "Save button of user " + rowNum, 15);
    }

    public void handleStorageAddSuccess() {
        String successMessage = getText(By.xpath("//div[@id='notifymessage']/div/span"), 30);
        if (successMessage.equalsIgnoreCase("Storage assigned to user successfully")) {
            click(By.xpath("//button[@id='closenotifymessage']"), "Close Popup", 20);
            waitForLoader(20);
        } else {
            getTest().log(LogStatus.FAIL, "Storage assigned success message is not displayed");
            logger.info("Storage assigned success message is not displayed");
            takeScreenshot("StorageAssignSuccess");
        }
    }

    public void clickEditIconSingle() {
        clickEditIcon(1);
    }

    public void enterAllowedStorageSingle() {
        enterAllowedStorage(1);
    }

    public void clickSaveButtonSingle() {
        clickSaveButton(1);
    }

    public void addStorageForDiffUser() {
        for (int i = 2; i <= 3; i++) {
            clickEditIcon(i);
            enterAllowedStorage(i);
            clickSaveButton(i);
            handleStorageAddSuccess();
        }
    }

    public void verifyTotalAllowedCount() {
        double countInList = 0;
        List<WebElement> listOFUser = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[7]/input"), 30);
        for (WebElement ele : listOFUser) {
            double count = Integer.parseInt(ele.getAttribute("value"));
            countInList = countInList + count;
        }
        String totalInTable = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[@id='trlTotal']//td//span[@id='spnTotalAllowed']"), 30).replaceAll(",","");
        sumInTable = Double.parseDouble(totalInTable);
        if (sumInTable == countInList) {
            getTest().log(LogStatus.PASS, "The Allotted counts of each user " + countInList + " is equal to the total count in the table - " + sumInTable);
            logger.info("The Allotted counts of each user " + countInList + " is equal to the total count in the table - " + sumInTable);
        } else {
            getTest().log(LogStatus.FAIL, "The Allotted counts of each user is not equal to the total count in the table");
            logger.info("The Allotted counts of each user is not equal to the total count in the table");
            takeScreenshot("UserTableCount");
        }
    }

    public void verifyAssignerToUserCount() {
        String countInHeader = getText(By.xpath("//span[text()='Assigned To User']//parent::h3/span[@class]"), 30).replaceAll(",","");
        double countInTitle = Double.parseDouble(countInHeader);
        if (sumInTable == countInTitle) {
            getTest().log(LogStatus.PASS, "The sum of allotted storage displayed in the table " + sumInTable + " is equal to the value displayed along with the  \"Assigned To User\" header - " + countInTitle);
            logger.info("The sum of allotted storage displayed in the table " + sumInTable + " is equal to the value displayed along with the  \"Assigned To User\" header - " + countInTitle);
        } else {
            getTest().log(LogStatus.PASS, "The sum of allotted storage displayed in the table " + sumInTable + " is not equal to the value displayed along with the  \"Assigned To User\" header - " + countInTitle);
            logger.info("The sum of allotted storage displayed in the table " + sumInTable + " is not equal to the value displayed along with the  \"Assigned To User\" header - " + countInTitle);
            takeScreenshot("TableHeaderCount");
        }
    }

    public void expandDepartmentSearch() {
        click(By.xpath("//span[@id='departmentIdHeading']"), "Department search", 15);
    }

    public void getDepartmentToSearch() {
        departmentToSearch = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[4]/span"), 30).trim();
    }

    public void selectDepartmentInSearchField() {
        click(By.xpath("//div[@id='collapseOneDepartment']//label[text()='" + departmentToSearch + "']"), "Department option", 15);
    }

    public void resetStorageSearch() {
        click(By.cssSelector("span#reset"), "Storage search reset", 30);
    }

    public void verifySearchedDepartment() {
        List<String> expectedDepartmentList = new ArrayList<>();
        int iteration = 0;
        List<WebElement> searchedDepartmentLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[4]/span"), 30);
        if (searchedDepartmentLocator.size() != 0) {
            for (WebElement departmentLocator : searchedDepartmentLocator) {
                if (departmentLocator.isDisplayed()) {
                    expectedDepartmentList.add(departmentLocator.getText().trim());
                }
            }
            for (String expectedDepartment : expectedDepartmentList) {
                iteration++;
                if (!expectedDepartment.equalsIgnoreCase(departmentToSearch)) {
                    getTest().log(LogStatus.FAIL, expectedDepartment + " is displayed when " + departmentToSearch + " department is searched");
                    logger.info(expectedDepartment + " is displayed when " + departmentToSearch + " department is searched");
                    takeScreenshot("DepartmentNameSearch");
                } else if (expectedDepartmentList.size() == iteration) {
                    getTest().log(LogStatus.PASS, "Selected department " + departmentToSearch + " is displayed as expected when search button is clicked");
                    logger.info("Selected department " + departmentToSearch + " is displayed as expected when search button is clicked");
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "Searched result for " + departmentToSearch + " is not displayed");
            logger.info("Searched result for " + departmentToSearch + " is not displayed");
            takeScreenshot("DepartmentSearch");
        }
    }

    public void expandDesignationSearchField() {
        click(By.xpath("//span[@id='designationHeading']"), "Designation Search field", 15);
    }

    public void getDesignationToSearch() {
        String designationName;
        List<WebElement> designationLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[3]/span"), 30);
        for (WebElement designation : designationLocators) {
            designationName = designation.getText().trim();
            if (!designationName.equals("--")) {
                designationToSearch = designationName;
                break;
            }
        }
    }

    public void selectDesignation() {
        click(By.xpath("//div[@id='collapseOneDesignation']//label[text()='" + designationToSearch + "']"), "Designation Search option", 20);
    }

    public void verifySearchedDesignation() {
        List<String> expectedDesignationList = new ArrayList<>();
        int iteration = 0;
        List<WebElement> designationLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[3]/span"), 30);
        if (designationLocators.size() != 0) {
            for (WebElement designation : designationLocators) {
                if (designation.isDisplayed()) {
                    expectedDesignationList.add(designation.getText().trim());
                }
            }
            for (String expectedDesignation : expectedDesignationList) {
                iteration++;
                if (!expectedDesignation.equalsIgnoreCase(designationToSearch)) {
                    getTest().log(LogStatus.FAIL, expectedDesignation + " is displayed when " + designationToSearch + " designation is searched");
                    logger.info(expectedDesignation + " is displayed when " + designationToSearch + " designation is searched");
                    takeScreenshot("DesignationSearch");
                } else if (expectedDesignationList.size() == iteration) {
                    getTest().log(LogStatus.PASS, "Searched result for " + designationToSearch + " designation is displayed as expected when click on the search button");
                    logger.info("Searched result for " + designationToSearch + " designation is displayed as expected when click on the search button");
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "Result for " + designationToSearch + " designation id not displayed");
            logger.info("Result for " + designationToSearch + " designation id not displayed");
            takeScreenshot("DesignationSearch");
        }
    }

    public void expandUserNameSearchField() {
        click(By.xpath("//span[@id='SearchingClientHeading']"), "User Name search field", 15);
    }

    public void getUserNameToSearch() {
        userNameToSearch = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[2]/span"), 20).trim();
    }

    public void enterUserNameInSearchField() {
        enter(By.id("User"), userNameToSearch, "User Name Search box", 20);
    }

    public void verifySearchedUserName() {
        List<String> expectedUserNameList = new ArrayList<>();
        int iteration = 0;
        List<WebElement> searchedUserNameLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//span/input[contains(@name,'UserName')]//parent::span"), 30);
        if (searchedUserNameLocators.size() != 0) {
            for (WebElement searchedUserName : searchedUserNameLocators) {
                if (searchedUserName.isDisplayed()) {
                    expectedUserNameList.add(searchedUserName.getText().trim());
                }
            }
            for (String expectedUserName : expectedUserNameList) {
                iteration++;
                if (!expectedUserName.equalsIgnoreCase(userNameToSearch)) {
                    getTest().log(LogStatus.FAIL, expectedUserName + " is displayed when " + userNameToSearch + " user is searched");
                    logger.info(expectedUserName + " is displayed when " + userNameToSearch + " user is searched");
                    takeScreenshot("UserNameSearch");
                } else if (expectedUserNameList.size() == iteration) {
                    getTest().log(LogStatus.PASS, "Search result for " + userNameToSearch + " is displayed as expected when search button is clicked");
                    logger.info("Search result for " + userNameToSearch + " is displayed as expected when search button is clicked");
                }
            }
        } else {
            getTest().log(LogStatus.FAIL, "Search result for " + userNameToSearch + " is not displayed");
            logger.info("Search result for " + userNameToSearch + " is not displayed");
            takeScreenshot("UserNameSearch");
        }
    }

    public void expandConsumedStorageField() {
        click(By.xpath("//span[@id='SearchingConsumedHeading']"), "Consumed Storage field", 20);
    }

    public void getConsumedStorageFromTable() {
        consumedStorageToSearch = getText(By.xpath("//table[@id='tblStorage']//tbody//tr[1]//td[6]//span"), 30).trim();
    }

    public void enterConsumedStorageInSearchBox() {
        enter(By.xpath("//input[@id='txtConsumedStorage']"), consumedStorageToSearch, "Consumed storage search field", 20);
    }

    public void clickSearchButton() {
        click(By.cssSelector("#Go"), "Search Button", 20);
        waitForLoad(30);
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

    public void userNameAscending() {
        userNameSorting("ascending");
    }

    public void userNameDescending() {
        userNameSorting("descending");
    }

    public void userNameSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#USER_NAME"), "User Name", 20);

        List<WebElement> userNameLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[2]/span[contains(@class,'dynamic-container')]"), 30);
        for (WebElement userName : userNameLocators) {
            actualSortedArray.add(userName.getText().trim());
        }

        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "UserName");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "UserName");
        }
    }

    public void designationAscending() {
        designationSorting("ascending");
    }

    public void designationDescending() {
        designationSorting("descending");
    }

    public void designationSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#DESIGNATION_NAME"), "Designation", 20);

        List<WebElement> designationLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[3]/span[contains(@class,'dynamic-container')]"), 30);
        for (WebElement designation : designationLocators) {
            actualSortedArray.add(designation.getText().trim());
        }
        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "Designation");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "Designation");
        }
    }

    public void departmentAscending() {
        departmentSorting("ascending");
    }

    public void departmentDescending() {
        departmentSorting("descending");
    }

    public void departmentSorting(String order) {
        List<String> actualSortedArray = new ArrayList<>();

        click(By.cssSelector("#DEPARTMENT_NAME"), "Department header", 20);

        List<WebElement> departmentLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[4]/span[contains(@class,'dynamic-container')]"), 30);
        for (WebElement department : departmentLocator) {
            actualSortedArray.add(department.getText().trim());
        }
        if (order.equalsIgnoreCase("ascending")) {
            verifyAscendingOrder(actualSortedArray, "Department");
        } else if (order.equalsIgnoreCase("descending")) {
            verifyDescendingOrder(actualSortedArray, "Department");
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

        List<WebElement> statusLocators = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[5]/a/span"), 30);
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

        List<WebElement> consumedStorageLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[5]/a/span"), 30);
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

        List<WebElement> consumedStorageLocator = findMultipleElement(By.xpath("//table[@id='tblStorage']//tbody//tr//td[7]/input"), 30);
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
        List<String> result = new ArrayList<>();
        List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
        expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
        for(int i =0; i<expectedSortedList.size();i++)
        {
            if (expectedSortedList.get(i).equalsIgnoreCase(actualSortedArray.get(i)))
            {
                result.add("true");
            }
            else
            {
                result.add("false");
            }
        }
        if (!result.contains("false")) {
            getTest().log(LogStatus.PASS, fieldName + "s are sorted in ascending Order when click the " + fieldName + " header for one time");
            logger.info(fieldName + "s are sorted in ascending Order when click the " + fieldName + " header for one time");
        } else {
            getTest().log(LogStatus.FAIL, fieldName + "s are not sorted in ascending Order when click the " + fieldName + " header for one time");
            logger.info(fieldName + "s are not sorted in ascending Order when click the " + fieldName + " header for one time");
            takeScreenshot(fieldName + "Ascending");
        }
    }

    public void verifyDescendingOrder(List<String> actualSortedArray, String fieldName) {
        List<String> result = new ArrayList<>();
        List<String> expectedSortedList = new ArrayList<>(actualSortedArray);
        expectedSortedList.sort(String.CASE_INSENSITIVE_ORDER);
        Collections.reverse(expectedSortedList);
        for(int i =0; i<expectedSortedList.size();i++)
        {
            if (expectedSortedList.get(i).equalsIgnoreCase(actualSortedArray.get(i)))
            {
                result.add("true");
            }
            else
            {
                result.add("false");
            }
        }
        if (!result.contains("false")) {
            getTest().log(LogStatus.PASS, fieldName + "s are sorted in descending Order when click the " + fieldName + " header for two times");
            logger.info(fieldName + "s are sorted in descending Order when click the " + fieldName + " header for two times");
        } else {
            getTest().log(LogStatus.FAIL, fieldName + "s are not sorted in descending Order when click the " + fieldName + " header for two times");
            logger.info(fieldName + "s are not sorted in descending Order when click the " + fieldName + " header for two times");
            takeScreenshot(fieldName + "Descending");
        }
    }
}
