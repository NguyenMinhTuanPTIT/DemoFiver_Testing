package Pages;

import Base.BasePage;
import Locators.*;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    public LoginPage(Page page, String baseUrl) {
        super(page, baseUrl);
    }

    public void unseenPassword(String password) {
        navigate(UrlLocator.LOGIN_URL);
        fill(LoginLocator.password,password);
        click(LoginLocator.unseenPasswordBtn);
    }
    public void login(String email, String password) {
        navigate(UrlLocator.LOGIN_URL);
        fill(LoginLocator.email,email);
        fill(LoginLocator.password,password);
        click(LoginLocator.btnLogin);
    }
    public void transferToRegisterPage() {
        navigate(UrlLocator.LOGIN_URL);
        click(LoginLocator.aResgister);
    }


}
