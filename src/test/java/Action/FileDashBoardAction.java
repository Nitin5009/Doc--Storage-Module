package Action;

import org.openqa.selenium.WebDriver;
import pageobjects.FileDashBoardPage;
import pageobjects.UserStoragePage;

public class FileDashBoardAction {
    WebDriver driver;
    FileDashBoardPage fileDashBoard;
    UserStoragePage userStorage;

    public FileDashBoardAction(WebDriver driver) {
        this.driver = driver;
        this.fileDashBoard = new FileDashBoardPage(driver);
        this.userStorage = new UserStoragePage(driver);
    }

    public void navigateToFileDashBoardPage() {
        userStorage.clickFullMenuDropDown();
        fileDashBoard.openFileDashboard();
    }

    public void ValidationMsg() {
        fileDashBoard.navigateToManageFolderPage();
        fileDashBoard.clickFolderSaveButton();
        fileDashBoard.verifyFolderNameErrorMessage();
        fileDashBoard.verifyFolderDescErrorMessage();
        fileDashBoard.clickFolderCancelButton();
    }

    public void createFolder() {
        fileDashBoard.navigateToManageFolderPage();
        fileDashBoard.enterFolderName();
        fileDashBoard.selectDepartment();
        fileDashBoard.verifyAvailableUserList();
        fileDashBoard.assignUser();
        fileDashBoard.addDescription();
        fileDashBoard.clickFolderSaveButton();
        fileDashBoard.handleFolderCreateSuccessMsg();
        fileDashBoard.clickCreatedFolder();
    }

    public void updateFolder() {
        fileDashBoard.verifyFolderNameColor();
        fileDashBoard.clickEditIcon();
        fileDashBoard.updateDescription();
        fileDashBoard.clickFolderSaveButton();
        fileDashBoard.handleFolderUpdateSuccessMsg();
        fileDashBoard.clickCreatedFolder();
        fileDashBoard.clickEditIcon();
        fileDashBoard.verifyUpdatedDescription();
    }

    public void uploadSingleFile() {
        fileDashBoard.uploadSingleFile();
        fileDashBoard.clickStartUpload();
    }

    public void fileUploadAndReset() {
        uploadSingleFile();
        fileDashBoard.verifySingleFile();
        fileDashBoard.clickResetButton();
        fileDashBoard.verifyResetFile();
    }

    public void uploadVerifyMultiFiles() {
        fileDashBoard.addMultipleFiles();
        fileDashBoard.clickStartUpload();
        fileDashBoard.verifyMultipleFiles();
    }

    public void cardViewListView() {
        fileDashBoard.clickCardViewOption();
        fileDashBoard.verifyCardView();
        fileDashBoard.clickListViewOption();
        fileDashBoard.verifyListView();
    }

    public void bulkDownloadVerify() {
        fileDashBoard.clickCheckBox();
        fileDashBoard.clickBulkDownloadButton();
        fileDashBoard.confirmAndDownload();
        fileDashBoard.verifyDownloadedFile();
    }

    public void singleDownloadVerify() {
        fileDashBoard.clickActionIcon();
        fileDashBoard.clickSingleDownloadIcon();
        fileDashBoard.verifyDownloadedFile();
    }

    public void BulkMove() {
        fileDashBoard.clickCheckBox();
        fileDashBoard.clickBulkMoveButton();
        fileDashBoard.confirmationForMove();
        fileDashBoard.selectDestinationFolder();
        fileDashBoard.ClickConfirmButton();
        fileDashBoard.clickSubmitConfirmation();
        fileDashBoard.verifyMoveStatus();
        fileDashBoard.closeFileStatusPopup();
    }

    public void renameOfFile() {
        fileDashBoard.clickRenameOption();
        fileDashBoard.updateFileName();
        fileDashBoard.saveUpdatedName();
        fileDashBoard.handleFileNameUpdateSuccess();
        fileDashBoard.verifyUpdatedFileName();
    }

    public void BulkClone() {
        fileDashBoard.clickCheckBox();
        fileDashBoard.clickCloneButton();
        fileDashBoard.confirmationForClone();
        fileDashBoard.selectDestinationFolder();
        fileDashBoard.ClickConfirmButton();
        fileDashBoard.clickSubmitConfirmation();
        fileDashBoard.verifyCloneStatus();
        fileDashBoard.closeFileStatusPopup();
    }

    public void BulkDelete() {
        fileDashBoard.clickCreatedFolder();
        fileDashBoard.clickCheckBox();
        fileDashBoard.clickBulkDeleteButton();
        fileDashBoard.confirmationForDelete();
        fileDashBoard.handleFileDeleteSuccess();
        fileDashBoard.verifyDeletedFile();
    }
}
