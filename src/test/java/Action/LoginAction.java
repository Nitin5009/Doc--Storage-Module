package Action;
import org.openqa.selenium.WebDriver;
import pageobjects.UserLoginPage;

public class LoginAction  {
    WebDriver driver;
    UserLoginPage userLoginPage;
    public LoginAction(WebDriver driver)
    {
        this.driver = driver;
        userLoginPage = new UserLoginPage(driver);
    }
    public void enterCredentials()
    {
        userLoginPage = new UserLoginPage(driver);
        userLoginPage.enterUserName();
        userLoginPage.enterPassword();
        userLoginPage.acceptPrivacyPolicy();
    }
    public void submit()
    {
        userLoginPage.clickLogin();
    }
    public void logoutLogin(){
        userLoginPage.forcefulLogOutLogin();
    }

}
