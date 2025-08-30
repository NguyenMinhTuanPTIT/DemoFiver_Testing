package Tests;

import Base.BaseTest;
import Data.LoginData;
import Locators.LoginLocator;
import Locators.UrlLocator;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(page);
        page.navigate(UrlLocator.LOGIN_URL);
    }

    @Test
    public void testLoginWithValidData() {
        loginPage.login(LoginData.email, LoginData.password);
        page.waitForTimeout(5000);
        page.navigate(UrlLocator.profilePageUrl);

        Assert.assertTrue(page.url().contains("profile"), "Đăng nhập thất bại");
    }

    @Test
    public void testLoginWithWrongData() {
        loginPage.login(LoginData.wrongEmail, LoginData.wrongPassword);
        page.waitForTimeout(5000);
        Assert.assertTrue(loginPage.isErrorVisible(LoginLocator.loginFailedNotification),
                "Đăng nhập thất bại nhưng không hiển thị thông báo lỗi");
    }

    @Test
    public void testLoginWithNoData() {
        loginPage.login("", "");
        page.waitForTimeout(3000);

        Assert.assertTrue(loginPage.isErrorVisible(LoginLocator.emailEmptyNotification),
                "Thông báo 'Email không được bỏ trống !' phải hiển thị");
        Assert.assertTrue(loginPage.isErrorVisible(LoginLocator.passwordEmptyNotification),
                "Thông báo 'Password không được bỏ trống !' phải hiển thị");
    }

    @Test
    public void testLoginWithInvalidData() {
        loginPage.login(LoginData.invalidEmail, LoginData.invalidPassword);
        page.waitForTimeout(3000);

        Assert.assertTrue(loginPage.isErrorVisible(LoginLocator.emailInvalidNotification),
                "Thông báo 'Email không đúng định dạng !' phải hiển thị");
        Assert.assertTrue(loginPage.isErrorVisible(LoginLocator.passwordInvalidNotification),
                "Thông báo 'pass từ 6 - 32 ký tự !' phải hiển thị");
    }

    @Test
    public void testLoginWithMultipleTimesWrongData() {
        for (int i = 0; i < LoginData.wrongTimes; i++) {
            loginPage.login(LoginData.wrongEmail, LoginData.wrongPassword);
            page.waitForTimeout(3000);
            Assert.assertTrue(loginPage.isErrorVisible(LoginLocator.loginFailedNotification),
                    "Lần " + (i + 1) + ": Thông báo đăng nhập thất bại phải hiển thị");
        }

        // Kiểm tra chuyển hướng về trang chủ
        Assert.assertEquals(page.url(), "https://demo5.cybersoft.edu.vn/",
                "Sau " + LoginData.wrongTimes + " lần sai, phải chuyển về trang chủ");
    }

    @Test
    public void testUnseenPassword() {
        Assert.assertTrue(loginPage.isUnseenPasswordRunning(), "Unseen Password hoạt động đúng");
    }


    @Test
    public void testTransferToRegisterPage() {
        loginPage.transferToRegisterPage();
        Assert.assertTrue(page.url().contains("register"), "Đã điều hướng thành công đến trang đăng kí");
    }

    @Test
    public void testTransferToLoginPageByUser() {
        loginPage.login(LoginData.email, LoginData.password);
        page.waitForTimeout(5000);
        page.navigate(UrlLocator.LOGIN_URL);

        Assert.assertFalse(loginPage.isErrorVisible(LoginLocator.title),
                "Vẫn truy cập vào trang Login dù đang trong session đăng nhập");
    }
}
