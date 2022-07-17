package pageobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Drivers;
import utils.PropertiesLoader;
import utils.WebBasePage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static reporting.ComplexReportFactory.getTest;
import static utils.Errors.*;

public class FileDashBoardPage extends WebBasePage {

    UserStoragePage userStorage;
    String folderName;
    String destinationFolder;
    String checkBoxSelectedFile;
    String updatedDescription;
    String fileNameToBeUpdate;
    String fileNameBeforeUpdate;
    String pattern = "yyyyMMddHHmmss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String dateValue = simpleDateFormat.format(new Date());
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
    String fileOne = prop.getProperty("testfileDoc");
    String fileTwo = prop.getProperty("testfilePDF");
    String fileThree = prop.getProperty("testfilejpg");
    String fileFour = prop.getProperty("testfilexls");
    int filesInDirectory;

    public FileDashBoardPage(WebDriver driver) {
        super(driver, "File Storage Page");
        this.userStorage = new UserStoragePage(driver);
    }

    public void openFileDashboard() {
        userStorage.clickDocStorageOption();
        WebElement fileDashBoardOption = findElementVisibility(By.xpath("//ul[@data-p-name='Doc. Storage']//a[text()='File Dashboard']"), 30);
        if (fileDashBoardOption != null) {
            click(By.xpath("//ul[@data-p-name='Doc. Storage']//a[text()='File Dashboard']"), "File Dahsboard option", 15);
        } else {
            openFileDashboard();
        }
    }

    public void navigateToManageFolderPage() {
        click(By.cssSelector("a#aAddFolder"), "Add Folder icon", 15);
        waitForLoad(10);
    }

    public void clickFolderSaveButton() {
        click(By.xpath("//button[contains(@class,'btn-success')]"), "Save button", 15);
    }

    public void clickFolderCancelButton() {
        click(By.xpath("//div[contains(@class,'text-right')]//a[contains(@class,'btn-danger')]"), "Cancel button", 15);
    }

    public void verifyFolderNameErrorMessage() {
        WebElement folderNameError = findElementVisibility(By.xpath("//span[contains(@class,'field-validation')]/span[@for='FolderName']"), 30);
        if (folderNameError != null && folderNameError.getText().equals(folderNameErrorMsg)) {
            getTest().log(LogStatus.PASS, "Error message for Folder name field is displayed as expected and the message is - " + folderNameError.getText());
            logger.info("Error message for Folder name field is displayed as expected and the message is - " + folderNameError.getText());
        } else {
            getTest().log(LogStatus.FAIL, "Error message for Folder name field is not displayed");
            logger.info("Error message for Folder name field is not displayed");
            takeScreenshot("FolderNameError");
        }
    }

    public void verifyFolderDescErrorMessage() {
        WebElement folderDescError = findElementVisibility(By.xpath("//span[contains(@class,'field-validation')]/span[@for='FolderDesc']"), 30);
        if (folderDescError != null && folderDescError.getText().equals(folderDescErrorMsg)) {
            getTest().log(LogStatus.PASS, "Error message for Folder description field is displayed as expected and the message is - " + folderDescError.getText());
            logger.info("Error message for Folder description field is displayed as expected and the message is - " + folderDescError.getText());
        } else {
            getTest().log(LogStatus.FAIL, "Error message for Folder description field is not displayed");
            logger.info("Error message for Folder description field is not displayed");
            takeScreenshot("FolderDescError");
        }
    }

    public void enterFolderName() {
        folderName = "F" + dateValue;
        enter(By.cssSelector("#FolderName"), folderName, "Folder Name", 20);
    }

    public void addDescription() {
        enterDescription("Description");
    }

    public void enterDescription(String description) {
        enter(By.cssSelector("#FolderDesc"), description + dateValue, "Folder Description", 20);
    }

    public void selectDepartment() {
        selectValueWithText(By.cssSelector("select#DepartmentId"), "Utility Technician", "Department", 20);
    }

    public void verifyAvailableUserList() {
        WebElement userList = findElementVisibility(By.cssSelector("ul#divAvailUser>li>a.addUser"), 30);
        if (userList != null) {
            getTest().log(LogStatus.PASS, "The available user list is displayed as expected");
            logger.info("The available user list is displayed as expected");
        } else {
            getTest().log(LogStatus.FAIL, "Available user list is not displayed");
            logger.info("Available user list is not displayed");
            takeScreenshot("AvailableUserList");
        }
    }

    public void assignUser() {
        int iteration = 0;
        String userName;
        String assignedUser;
        List<WebElement> availableUserLocator = findMultipleElement(By.xpath("//a[@class='float-right addUser']//parent::li/strong"), 30);
        for (WebElement availableUser : availableUserLocator) {
            iteration++;
            userName = availableUser.getText().trim();
            click(By.xpath("//li//strong[text()='" + userName + "']/parent::li/a"), userName, 20);
            assignedUser = getText(By.xpath("//ul[@id='divAssignedUser']/li[" + iteration + "]/strong"), 30).trim();
            if (userName.equalsIgnoreCase(assignedUser)) {
                getTest().log(LogStatus.PASS, assignedUser + " is assigned to the folder when click on the + icon");
                logger.info(assignedUser + " is assigned to the folder when click on the + icon");
            } else {
                getTest().log(LogStatus.FAIL, assignedUser + " is not assigned to the folder when click on the + icon");
                logger.info(assignedUser + " is assigned to the folder when click on the + icon");
                takeScreenshot("AssignedUser");
            }
            if (availableUserLocator.size() / 2 == iteration) {
                break;
            }
        }
    }

    public void handleSuccessMsg(String expectedMsg, String process) {
        String successMsg = getText(By.xpath("//div[@id='notifymessage']/div/span"), 40);
        if (successMsg.equalsIgnoreCase(expectedMsg)) {
            click(By.xpath("//button[@id='closenotifymessage']"), "Success Msg Close", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Success message for " + process + " is not displayed");
            logger.info("Success message for " + process + " is not displayed");
            takeScreenshot("SuccessMsg");
        }
    }

    public void handleFolderCreateSuccessMsg() {
        handleSuccessMsg("Folder has been successfully added.", "folder creation");
    }

    public void clickCreatedFolder() {
        WebElement createdFolderName = findElementVisibility(By.xpath("//span[@class='folder']/a[@foldername='" + folderName + "']"), 30);
        if (createdFolderName != null) {
            click(By.xpath("//span[@class='folder']/a[@foldername='" + folderName + "']"), "Folder - " + folderName, 20);
        } else {
            getTest().log(LogStatus.FAIL, "Created " + folderName + " folder is not displayed in the folder list");
            logger.info("Created " + folderName + " folder is not displayed in the folder list");
            takeScreenshot("CreatedFolder");
        }
    }

    public void verifyFolderNameColor() {
        WebElement createdFolderName = findElementVisibility(By.xpath("//span[@class='folder']/a[@foldername='" + folderName + "']"), 30);
        String folderNameColour = createdFolderName.getCssValue("color");
        if (folderNameColour.equalsIgnoreCase("rgba(255, 0, 0, 1)")) {
            getTest().log(LogStatus.PASS, "The folder name is changed to Red color when click on it");
            logger.info("The folder name is changed to Red color when click on it");
        } else {
            getTest().log(LogStatus.FAIL, "The folder name is not changed to Red color when click on it");
            logger.info("The folder name is not changed to Red color when click on it");
        }
    }

    public void clickEditIcon() {
        scrollToWebelement(By.xpath("//span[text()='Storage Capacity']"),"Storage Capacity menu bar");
        click(By.cssSelector("a#aEditFolder"), "Edit Icon", 20);
    }

    public void updateDescription() {
        enterDescription("Updated Description");
        updatedDescription = getAtribute(By.xpath("//label[@for='FolderDesc']/parent::div/textarea"), "value", 20);
    }

    public void handleFolderUpdateSuccessMsg() {
        handleSuccessMsg("Folder has been successfully updated.", "folder update");
    }

    public void verifyUpdatedDescription() {
        String actualDescription = getText(By.xpath("//label[@for='FolderDesc']/parent::div/textarea"), 20);
        if (actualDescription.equalsIgnoreCase(updatedDescription)) {
            getTest().log(LogStatus.PASS, "Description for the folder is successfully updated");
            logger.info("Description for the folder is successfully updated");
        } else {
            getTest().log(LogStatus.FAIL, "Description for the folder is not updated");
            logger.info("Description for the folder is not updated");
            takeScreenshot("FolderDescription");
        }
    }

    public void uploadSingleFile() {
        String uploadOne = filePath + fileOne;
        uploadDoc(By.xpath("//div[contains(@class,'plupload')]/input[@type='file']"), uploadOne, "File Upload", 20);
    }

    public void clickStartUpload() {
        click(By.xpath("//a[contains(@class,'plupload_start')]"), "Start Upload", 20);
        findElementVisibility(By.xpath("//a[contains(@class,'plupload_reset')]"), 180);
    }

    public void verifySingleFile() {
        String[] expectedArray = {fileOne};
        verifyUploadedFiles(expectedArray);
    }

    public void clickResetButton() {
        click(By.xpath("//a[contains(@class,'plupload_reset')]"), "Reset button", 20);
    }

    public void verifyResetFile() {
        int resetUploadedFiles = findMultipleElement(By.cssSelector("#plFileList_filelist>li"),5).size();
        if(resetUploadedFiles == 0)
        {
            getTest().log(LogStatus.PASS,"The uploaded files are reset successfully when click on the reset button");
            logger.info("The uploaded files are reset successfully when click on the reset button");
        }
        else
        {
            getTest().log(LogStatus.FAIL,"The uploaded files are not reset when click on the reset button");
            logger.info("The uploaded files are not reset when click on the reset button");
            takeScreenshot("FileReset");
        }
    }

    public void addMultipleFiles() {
        String uploadOne = filePath + fileOne;
        String uploadTwo = filePath + fileTwo;
        String uploadThree = filePath + fileThree;
        String uploadFour = filePath + fileFour;
        uploadDoc(By.xpath("//div[contains(@class,'plupload')]/input[@type='file']"), uploadOne + "\n" + uploadTwo + "\n" + uploadThree + "\n" + uploadFour, "File Upload", 20);
    }

    public void verifyMultipleFiles() {
        String[] expectedArray = {fileOne, fileTwo, fileThree, fileFour};
        verifyUploadedFiles(expectedArray);
    }

    public void verifyUploadedFiles(String[] expectedArray) {
        String actualUploadedFileName;
        int iteration = 0;
        List<WebElement> uploadedFilesLocator = findMultipleElement(By.xpath("//table[@id='tblFileList']//tbody/tr/td[2]/span/a"), 30);
        for (WebElement uploadedFiles : uploadedFilesLocator) {
            for (String expectedFileName : expectedArray) {
                iteration++;
                actualUploadedFileName = uploadedFiles.getText().trim();
                if (actualUploadedFileName.equalsIgnoreCase(expectedFileName)) {
                    getTest().log(LogStatus.PASS, "Uploaded file " + expectedFileName + " is displayed in the file list");
                    logger.info("Uploaded file " + expectedFileName + " is displayed in the file list");
                    iteration = 0;
                    break;
                } else if (iteration == expectedArray.length) {
                    getTest().log(LogStatus.FAIL, "Uploaded file " + expectedFileName + " is not displayed in the file list");
                    logger.info("Uploaded file " + expectedFileName + " is not displayed in the file list");
                    takeScreenshot("UploadeFileName");
                }
            }
        }
    }

    public void clickCardViewOption() {
        waitForLoader(10);
        scrollToWebelement(By.xpath("//a[@id='a_changeView']//span[text()='Card View']"),"Card View Option");
        click(By.xpath("//a[@id='a_changeView']//span[text()='Card View']"), "Card view option", 40);
    }

    public void verifyCardView() {
        WebElement cardView = findElementVisibility(By.xpath("//div[@id='div_forCardView']"), 30);
        if (cardView != null) {
            getTest().log(LogStatus.PASS, "The uploaded documents are displayed in the card view when click Card view option");
            logger.info("The uploaded documents are displayed in the card view when click Card view option");
        } else {
            getTest().log(LogStatus.FAIL, "The uploaded documents are not displayed in the card view when click Card view option");
            logger.info("The uploaded documents are not displayed in the card view when click Card view option");
            takeScreenshot("CardView");
        }
    }

    public void clickListViewOption() {
        waitForLoader(20);
        scrollToWebelement(By.xpath("//a[@id='a_changeView']//span[text()='List View']"), "List View Option");
        click(By.xpath("//a[@id='a_changeView']//span[text()='List View']"), "List view option", 20);
    }

    public void verifyListView() {
        scrollToWebelement(By.xpath("//table[@id='tblFileList']//tbody//tr[1]"), "uploadedFile");
        WebElement listView = findElementVisibility(By.xpath("//table[@id='tblFileList']//tbody//tr[1]"), 30);
        if (listView != null) {
            getTest().log(LogStatus.PASS, "The uploaded documents are displayed in the list view when click List view option");
            logger.info("The uploaded documents are displayed in the list view when click list view option");
        } else {
            getTest().log(LogStatus.FAIL, "The uploaded documents are not displayed in the list view when click list view option");
            logger.info("The uploaded documents are not displayed in the list view when click list view option");
            takeScreenshot("listView");
        }
    }

    public void clickCheckBox() {
        scrollToWebelement(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td/span/div"), "Uploaded File");
        checkBoxSelectedFile = getText(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td[2]/span/a"), 20);
        click(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td/span/div"), "Check box", 20);
    }

    public void clickBulkDownloadButton() {
        click(By.cssSelector("a#aDownloadAll"), "Bulk Download icon", 20);
    }

    public void removeFileIfExists() {
        String downloadPath = Drivers.path;
        File dir = new File(downloadPath + checkBoxSelectedFile);
        if (dir.exists()) {
            dir.delete();
        }
        File[] dir2 = new File(downloadPath).listFiles();
        filesInDirectory = dir2.length;
    }

    public void confirmAndDownload() {
        String actualConfirmationMsg = getText(By.xpath("//div[contains(@class,'alert-warning')]"), 40);
        if (actualConfirmationMsg.contains("download these file(s)")) {
            removeFileIfExists();
            click(By.xpath("//div[(contains(@class,'modal-confirm-footer'))]//button[contains(@class,'btn-success')]"), "Download Confirmation Yes", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation for file download is not displayed");
            logger.info("Confirmation for file download is not displayed");
            takeScreenshot("DownloadConfirmation");
        }
    }

    public void verifyDownloadedFile() {
        String downloadPath = Drivers.path;
        File dir = new File(downloadPath + checkBoxSelectedFile);
        File dir2 = new File(downloadPath);
        waitTillNewFile(dir2.toString(), filesInDirectory);
        int filesCountAfterDownload = dir2.listFiles().length;
        if (filesCountAfterDownload > filesInDirectory) {
            getTest().log(LogStatus.PASS, "Downloaded File is Exist");
            logger.info("Downloaded File is Exist");
        } else {
            getTest().log(LogStatus.FAIL, "Downloaded File is not Exist");
            logger.info("Downloaded File is not Exist");
        }
    }

    public void clickActionIcon() {
        waitForLoader(30);
        click(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td[contains(@class,'mobile-action')]/span"), "Action Icon", 20);
    }

    public void clickSingleDownloadIcon() {
        checkBoxSelectedFile = getText(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td[2]/span/a"), 30).trim();
        removeFileIfExists();
        click(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td[6]//a[contains(@class,'aFileDownload')]"), "Download Icon of file", 20);
    }

    public void clickBulkMoveButton() {
        click(By.cssSelector("a#aMoveAll"), "Bulk Move", 20);
    }

    public void confirmationPopup(String process) {
        String actualConfirmation = getText(By.xpath("//div[contains(@class,'notifybox')]//div[contains(@class,'alert-warning')]"), 30).trim();
        if (actualConfirmation.contains(process + " these file(s)")) {
            click(By.xpath("//div[contains(@class,'modal-confirm-footer')]//button[contains(@class,'btn-success')]"), "Confirm Ok ", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Confirmation for file " + process + " is not displayed when click on the " + process + " button");
            logger.info("Confirmation for file " + process + " is not displayed when click on the " + process + " button");
            takeScreenshot("Confirmation" + process);
        }
    }

    public void confirmationForMove() {
        confirmationPopup("move");
    }

    public void selectDestinationFolder() {
        List<WebElement> folderNameLocators = findMultipleElement(By.xpath("//div[@id='divDialogFiles']//a[@class='folderlink myfolder']"), 30);
        for (WebElement folder : folderNameLocators) {
            destinationFolder = folder.getAttribute("foldername");
            if (!destinationFolder.equalsIgnoreCase(folderName)) {
                folder.click();
                break;
            }
        }
    }

    public void ClickConfirmButton() {
        click(By.xpath("//button[@class='btn btn-success' and text()='Confirm']"), "Confirm", 20);
    }

    public void clickSubmitConfirmation() {
        String currentStatus = getText(By.xpath("//form[@id='frmCopyMoveConfirmation']//tbody//tr[1]//td[3]"), 30);
        if (currentStatus.equalsIgnoreCase("Already Exist")) {
            click(By.xpath("//label[normalize-space(text())='Replace']//parent::div/div"), "Replace option", 20);
            click(By.xpath("//button[@class='btn btn-success' and text()='Submit']"), "Submit", 20);
        } else if (currentStatus.equalsIgnoreCase("Pending")) {
            click(By.xpath("//button[@class='btn btn-success' and text()='Submit']"), "Submit", 20);
        } else {
            getTest().log(LogStatus.FAIL, "Document is not displayed with pending status");
            logger.info("Document is not displayed with pending status");
            takeScreenshot("PendingStatus");
        }
    }

    public void verifyStatus(String process) {
        String actualStatus = getText(By.xpath("//table[@id='tblFileStatus']//tbody//tr[1]/td[4]/span"), 30);
        if (actualStatus.equalsIgnoreCase("Successful")) {
            getTest().log(LogStatus.PASS, "The file " + checkBoxSelectedFile + " is " + process + " to the " + destinationFolder + " folder after click the submit button");
            logger.info("The file " + checkBoxSelectedFile + " is " + process + " to the " + destinationFolder + " folder after click the submit button");
        } else {
            getTest().log(LogStatus.FAIL, "The file " + checkBoxSelectedFile + " is not " + process + " to the " + destinationFolder + " folder after click the submit button");
            logger.info("The file " + checkBoxSelectedFile + " is not " + process + " moved to the " + destinationFolder + " folder after click the submit button");
            takeScreenshot("File" + process);
        }
    }

    public void verifyMoveStatus() {
        verifyStatus("Move");
    }

    public void closeFileStatusPopup() {
        click(By.xpath("//div[@aria-describedby='divDialogFileStatus']//button[@class='close']"), "File status popup close", 20);
    }

    public void clickRenameOption() {
        click(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td//a[@class='aFileRename actions-onclick']"), "Rename option", 20);
    }

    public void updateFileName() {
        fileNameToBeUpdate = "UpdateName" + dateValue;
        fileNameBeforeUpdate = getAtribute(By.cssSelector("input#FileName"), "value", 30);
        enter(By.cssSelector("input#FileName"), fileNameToBeUpdate, "File name", 20);
    }

    public void saveUpdatedName() {
        click(By.xpath("//div[@id='divRenameFiles']//parent::div//button[contains(@class,'btn-success')]"), "Save", 20);
    }

    public void handleFileNameUpdateSuccess() {
        handleSuccessMsg("File has been renamed successfully.", "file name update");
    }

    public void verifyUpdatedFileName() {
        String actualFIleName = getText(By.xpath("//table[@id='tblFileList']//tbody//tr[1]//td/span/a"), 20);
        if (actualFIleName.contains(fileNameToBeUpdate)) {
            getTest().log(LogStatus.PASS, "File name " + fileNameBeforeUpdate + " is updated to " + fileNameToBeUpdate + "");
            logger.info("File name " + fileNameBeforeUpdate + " is updated to " + fileNameToBeUpdate + "");
        } else {
            getTest().log(LogStatus.FAIL, "File name " + fileNameBeforeUpdate + " is not updated to " + fileNameToBeUpdate + "");
            logger.info("File name " + fileNameBeforeUpdate + " is not updated to " + fileNameToBeUpdate + "");
            takeScreenshot("FileNameUpdate");
        }
    }

    public void clickCloneButton() {
        click(By.cssSelector("a#aCopyAll"), "Clone All", 30);
    }

    public void confirmationForClone() {
        confirmationPopup("Copy");
    }

    public void verifyCloneStatus() {
        verifyStatus("Clone");
    }

    public void clickBulkDeleteButton() {
        click(By.cssSelector("a#aDeleteAll"), "bulk delete", 20);
    }

    public void confirmationForDelete() {
        confirmationPopup("delete");
    }

    public void handleFileDeleteSuccess() {
        handleSuccessMsg("File(s) has been deleted successfully.", "file delete");
    }

    public void verifyDeletedFile() {
        verifyRemovedFile(checkBoxSelectedFile, "delete");
    }

    public void verifyRemovedFile(String fileName, String process) {
        String actualFile;
        int iteration = 0;
        List<WebElement> availableFilesLocator = findMultipleElement(By.xpath("//table[@id='tblFileList']//tbody//tr//td[2]/span/a"), 10);
        if (availableFilesLocator.size() != 0) {
            for (WebElement availableFiles : availableFilesLocator) {
                iteration++;
                actualFile = availableFiles.getText();
                if (!actualFile.equalsIgnoreCase(fileName) && availableFilesLocator.size() == iteration) {
                    getTest().log(LogStatus.PASS, fileName + " is removed from the file list after " + process);
                    logger.info(fileName + " is removed from the file list after " + process);
                } else {
                    getTest().log(LogStatus.FAIL, fileName + " is not removed from the file list after " + process);
                    logger.info(fileName + " is not removed from the file list after " + process);
                    takeScreenshot(process + "File");
                }
            }
        } else {
            getTest().log(LogStatus.PASS, fileName + " is removed from the file list after " + process);
            logger.info(fileName + " is removed from the file list after " + process);
        }
    }
}
