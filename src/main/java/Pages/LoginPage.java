package Pages;

import Base.BasePage;
import Locators.*;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import Data.*;

public class LoginPage extends BasePage {
    public LoginPage(Page page) {
        super(page);
    }



    public void login(String email, String password) {
        typeByLocator(LoginLocator.email, email);
        page.waitForTimeout(1000);
        typeByLocator(LoginLocator.password, password);
        page.waitForTimeout(1000);
        clickByLocator(LoginLocator.loginBtn);
        page.waitForTimeout(4000);
    }

    public void transferToRegisterPage() {
        page.navigate(UrlLocator.LOGIN_PAGE);
        clickByLocator(LoginLocator.transferToRegisterBtn);
    }


    public boolean isUnseenPasswordRunning() {
        typeByLocator(LoginLocator.password, LoginData.password);
        String beforeType = getAttributeOfLocator(LoginLocator.password, "type");
        clickByLocator(LoginLocator.unseenPasswordBtn);
        String afterType = getAttributeOfLocator(LoginLocator.password, "type");
        clickByLocator(LoginLocator.unseenPasswordBtn);
        String finalType = getAttributeOfLocator(LoginLocator.password, "type");

        return beforeType.equals(finalType) && !beforeType.equals(afterType);
    }
}

