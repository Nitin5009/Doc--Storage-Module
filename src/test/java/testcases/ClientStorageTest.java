package testcases;

import Action.ClientStorageAction;
import Action.LoginAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class ClientStorageTest extends WebTestBase {

    @Test
    public void verifyStorageCounts() {
    	
        test = getTest("TC_Client_Storage_001");
        ClientStorageAction clientStorage = new ClientStorageAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        clientStorage.navigateToClientStoragePageAndExpand();
        clientStorage.allowedStorageFieldDisable();
        clientStorage.saveAllowedStorageForOneUser();
        clientStorage.saveAllowedStorageForMultiUser();
        clientStorage.verifyTableAndHeaderCount();
    }

    @Test
    public void verifySearchFunctionality() {
        test = getTest("TC_Client_Storage_002");
        ClientStorageAction clientStorage = new ClientStorageAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        clientStorage.navigateToClientStoragePage();
        clientStorage.searchAndVerifyClientName();
        clientStorage.searchAndVerifyConsumedStorage();
    }

    @Test
    public void verifySortingFunctionality() {
        test = getTest("TC_Client_Storage_003");
        ClientStorageAction clientStorage = new ClientStorageAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        clientStorage.navigateToClientStoragePageAndExpand();
        clientStorage.clientNameSorting();
        clientStorage.emailSorting();
        clientStorage.statusSorting();
        clientStorage.consumedStorageSorting();
        clientStorage.allowedStorageSorting();
    }
}
