package testcases;

import Action.FileDashBoardAction;
import Action.LoginAction;
import org.testng.annotations.Test;
import utils.WebTestBase;

import static reporting.ComplexReportFactory.getTest;

public class FileDashBoardTest extends WebTestBase {

    @Test(priority = 1)
    public void verifyMandatoryErrorMessage() {
        test = getTest("TC_File_DashBoard_ValidationMessage");
        FileDashBoardAction fileDashBoard = new FileDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        fileDashBoard.navigateToFileDashBoardPage();
        fileDashBoard.ValidationMsg();
    }

    @Test(priority = 2)
    public void createAndUpdateFolderFunctionality() {
        test = getTest("TC_File_DashBoard_CreateAndUpdateFolder");
        FileDashBoardAction fileDashBoard = new FileDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        fileDashBoard.navigateToFileDashBoardPage();
        fileDashBoard.createFolder();
        fileDashBoard.updateFolder();
    }

    @Test(priority = 3)
    public void fileUploadResetFunctionality() {
        test = getTest("TC_File_DashBoard_FileUploadAndReset");
        FileDashBoardAction fileDashBoard = new FileDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        fileDashBoard.navigateToFileDashBoardPage();
        fileDashBoard.createFolder();
        fileDashBoard.fileUploadAndReset();
        fileDashBoard.uploadVerifyMultiFiles();
    }

    @Test(priority = 6)
    public void fileDownloadRenameFunctionality() {
        test = getTest("TC_File_DashBoard_FileDownloadAndRename");
        FileDashBoardAction fileDashBoard = new FileDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        fileDashBoard.navigateToFileDashBoardPage();
        fileDashBoard.createFolder();
        fileDashBoard.uploadSingleFile();
        fileDashBoard.cardViewListView();
        fileDashBoard.bulkDownloadVerify();
        fileDashBoard.singleDownloadVerify();
        fileDashBoard.renameOfFile();
    }

    @Test(priority = 4)
    public void fileMoveFunctionality() {
        test = getTest("TC_File_DashBoard_FileMove");
        FileDashBoardAction fileDashBoard = new FileDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        fileDashBoard.navigateToFileDashBoardPage();
        fileDashBoard.createFolder();
        fileDashBoard.uploadSingleFile();
        fileDashBoard.BulkMove();
    }

    @Test(priority = 5)
    public void fileCloneAndDeleteFunctionality() {
        test = getTest("TC_File_DashBoard_FileCloneAndDelete");
        FileDashBoardAction fileDashBoard = new FileDashBoardAction(driver);
        LoginAction login = new LoginAction(driver);

        login.logoutLogin();
        fileDashBoard.navigateToFileDashBoardPage();
        fileDashBoard.createFolder();
        fileDashBoard.uploadSingleFile();
        fileDashBoard.BulkClone();
        fileDashBoard.BulkDelete();
    }
}
