package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.ClientStoragePage;
import pageobjects.UserStoragePage;

public class ClientStorageAction {
    WebDriver driver;
    ClientStoragePage clientStoragePage;
    UserStoragePage userStoragePage;

    public ClientStorageAction(WebDriver driver) {
        this.driver = driver;
        this.clientStoragePage = new ClientStoragePage(driver);
        this.userStoragePage = new UserStoragePage(driver);
    }

    public void navigateToClientStoragePage() {
        clientStoragePage.openClientStoragePage();
    }

    public void navigateToClientStoragePageAndExpand()
    {
        navigateToClientStoragePage();
        clientStoragePage.expandAndCollapse();
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
        clientStoragePage.handleStorageAddSuccess();
    }

    public void saveAllowedStorageForMultiUser() {
        clientStoragePage.addStorageForDiffUser();
    }

    public void verifyTableAndHeaderCount() {
        clientStoragePage.verifyTotalAllowedCount();
        clientStoragePage.verifyAssignerToClientCount();
    }

    public void searchAndVerifyClientName() {
        clientStoragePage.expandClientNameSearch();
        clientStoragePage.getClientNameToSearch();
        clientStoragePage.enterClientInSearchBox();
        userStoragePage.clickSearchButton();
        clientStoragePage.verifySearchedClientName();
        userStoragePage.resetStorageSearch();
    }

    public void searchAndVerifyConsumedStorage() {
        clientStoragePage.expandConsumedStorageField();
        clientStoragePage.getConsumedStorageFromTable();
        clientStoragePage.enterConsumedStorageInSearchBox();
        userStoragePage.clickSearchButton();
        clientStoragePage.verifySearchedConsumedStorage();
        userStoragePage.resetStorageSearch();
    }

    public void clientNameSorting() {
        clientStoragePage.clientNameAscending();
        clientStoragePage.clientNameDescending();
    }

    public void emailSorting() {
        clientStoragePage.emailAscending();
        clientStoragePage.emailDescending();
    }

    public void statusSorting() {
        clientStoragePage.statusAscending();
        clientStoragePage.statusDescending();
    }

    public void consumedStorageSorting() {
        clientStoragePage.consumedStorageAscending();
        clientStoragePage.consumedStorageDescending();
    }

    public void allowedStorageSorting() {
        clientStoragePage.allowedStorageAscending();
        clientStoragePage.allowedStorageDescending();
    }
}
