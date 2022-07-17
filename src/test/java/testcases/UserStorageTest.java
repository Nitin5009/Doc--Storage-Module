package testcases;

import Action.LoginAction;
import Action.UserStorageAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class UserStorageTest extends WebTestBase {

    @Test
    public void verifyStorageCounts() {
        test = getTest("TC_User_Storage_001");
        UserStorageAction userStorage = new UserStorageAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        userStorage.navigateToUserStoragePage();
        userStorage.allowedStorageFieldDisable();
        userStorage.saveAllowedStorageForOneUser();
        userStorage.saveAllowedStorageForMultiUser();
        userStorage.verifyTableAndHeaderCount();
    }

    @Test
    public void verifySearchFunctionality() {
        test = getTest("TC_User_Storage_002");
        UserStorageAction userStorage = new UserStorageAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        userStorage.navigateToUserStoragePage();
        userStorage.searchAndVerifyDepartment();
        userStorage.searchAndVerifyDesignation();
        userStorage.searchAndVerifyUserName();
        userStorage.searchAndVerifyConsumedStorage();
    }

    @Test
    public void verifySortingFunctionality() {
        test = getTest("TC_User_Storage_003");
        UserStorageAction userStorage = new UserStorageAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        userStorage.navigateToUserStoragePage();
        userStorage.userNameSorting();
        userStorage.designationSorting();
        userStorage.departmentSorting();
        userStorage.statusSorting();
        userStorage.consumedStorageSorting();
        userStorage.allowedStorageSorting();
    }
}
