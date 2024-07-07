package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    WebDriver driver;

    public HomePage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//a[@title='My Account']")
    WebElement InMyaAccount;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a [text()='Register']")
    WebElement InkRegister;

    @FindBy(linkText = "Login")
    WebElement linkLogin;




    public void clickMyAccount(){
        InMyaAccount.click();
    }
    public void clickRegister(){
        InkRegister.click();
    }
    public void clickLogin(){
        linkLogin.click();
    }
}
