package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.UserStoragePage;

public class UserStorageAction {
    WebDriver driver;
    UserStoragePage userStoragePage;

    public UserStorageAction(WebDriver driver) {
        this.driver = driver;
        userStoragePage = new UserStoragePage(driver);
    }

    public void navigateToUserStoragePage() {
        userStoragePage.openUserStoragePage();
    }

    public void allowedStorageFieldDisable() {
        userStoragePage.clickEditIconSingle();
        userStoragePage.enterAllowedStorageSingle();
        userStoragePage.clickCancelButton();
        userStoragePage.verifyAllowedStorageDisabled();
    }

    public void saveAllowedStorageForOneUser() {
        userStoragePage.clickEditIconSingle();
        userStoragePage.enterAllowedStorageSingle();
        userStoragePage.clickSaveButtonSingle();
        userStoragePage.handleStorageAddSuccess();
    }

    public void saveAllowedStorageForMultiUser() {
        userStoragePage.addStorageForDiffUser();
    }

    public void verifyTableAndHeaderCount() {
        userStoragePage.verifyTotalAllowedCount();
        userStoragePage.verifyAssignerToUserCount();
    }

    public void searchAndVerifyDepartment() {
        userStoragePage.expandDepartmentSearch();
        userStoragePage.getDepartmentToSearch();
        userStoragePage.selectDepartmentInSearchField();
        userStoragePage.clickSearchButton();
        userStoragePage.verifySearchedDepartment();
        userStoragePage.resetStorageSearch();
    }

    public void searchAndVerifyDesignation() {
        userStoragePage.expandDesignationSearchField();
        userStoragePage.getDesignationToSearch();
        userStoragePage.selectDesignation();
        userStoragePage.clickSearchButton();
        userStoragePage.verifySearchedDesignation();
        userStoragePage.resetStorageSearch();
    }

    public void searchAndVerifyUserName() {
        userStoragePage.expandUserNameSearchField();
        userStoragePage.getUserNameToSearch();
        userStoragePage.enterUserNameInSearchField();
        userStoragePage.clickSearchButton();
        userStoragePage.verifySearchedUserName();
        userStoragePage.resetStorageSearch();
    }

    public void searchAndVerifyConsumedStorage() {
        userStoragePage.expandConsumedStorageField();
        userStoragePage.getConsumedStorageFromTable();
        userStoragePage.enterConsumedStorageInSearchBox();
        userStoragePage.clickSearchButton();
        userStoragePage.verifySearchedConsumedStorage();
        userStoragePage.resetStorageSearch();
    }

    public void userNameSorting() {
        userStoragePage.userNameAscending();
        userStoragePage.userNameDescending();
    }

    public void designationSorting() {
        userStoragePage.designationAscending();
        userStoragePage.designationDescending();
    }

    public void departmentSorting() {
        userStoragePage.departmentAscending();
        userStoragePage.departmentDescending();
    }

    public void statusSorting() {
        userStoragePage.statusAscending();
        userStoragePage.statusDescending();
    }

    public void consumedStorageSorting() {
        userStoragePage.consumedStorageAscending();
        userStoragePage.consumedStorageDescending();
    }

    public void allowedStorageSorting() {
        userStoragePage.allowedStorageAscending();
        userStoragePage.allowedStorageDescending();
    }
}
