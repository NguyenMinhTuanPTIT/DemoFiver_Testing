package Tests;

import Base.*;
import Pages.*;
import Locators.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Data.*;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(page, UrlLocator.LOGIN_URL);
    }

    @Test
    public void testLoginWithValidCredentials() {
        loginPage.login(LoginData.Email, LoginData.password);
        page.navigate(UrlLocator.profilePageUrl);

        String currentEmail = page.locator(ProfileLocator.emailTitle).innerText();
        Assert.assertEquals(
                currentEmail,
                LoginData.Email,
                "Login Failed due to wrong Infomation"
        );
    }
}
