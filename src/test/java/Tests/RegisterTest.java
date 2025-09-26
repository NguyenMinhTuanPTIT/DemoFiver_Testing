// File: src/main/java/Tests/RegisterTest.java
package Tests;

import Base.BaseTest;
import Data.LoginData;
import Locators.*;
import Pages.LoginPage;
import Pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Data.RegisterData;

public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(page);
        registerPage = new RegisterPage(page);
        page.navigate(UrlLocator.JOIN_PAGE);
    }

    @Test
    public void testTransferToLoginPage() {
        registerPage.transferToLoginPage();
        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("login"), "Đã điều hướng thành công tới trang Login");
    }

    @Test
    public void testRegisterWithValidData() {
        registerPage.register(
                RegisterData.username,
                RegisterData.email,
                RegisterData.password,
                RegisterData.confirmPassword,
                RegisterData.phone,
                RegisterData.birthday,
                RegisterData.isMale,
                RegisterData.isConfirm
        );

        page.waitForTimeout(3000);
        // Nếu hệ thống redirect đến login thì thử login
        if (page.url().contains("login")) {
            loginPage.login(RegisterData.email, RegisterData.password);
        }
        page.waitForTimeout(3000);

        boolean isRegistered = page.url().contains("profile") || page.url().contains("dashboard");
        logger.info("Current URL after register: " + page.url());
        Assert.assertTrue(isRegistered, "Đã đăng kí thành công");
    }

    @Test
    public void testRegisterWithNoData() {
        registerPage.register("","","","","","",true,false);
        registerPage.clickByLocator(RegisterLocator.registerBtn);
        Assert.assertTrue(page.locator(RegisterLocator.usernameEmptyNotification).isVisible(),"Hiển thị thông báo lỗi");
        Assert.assertTrue(page.locator(RegisterLocator.emailEmptyNotification).isVisible(),"Hiển thị thông báo lỗi");
        Assert.assertTrue(page.locator(RegisterLocator.passwordEmptyNotification).isVisible(),"Hiển thị thông báo lỗi");
        Assert.assertTrue(page.locator(RegisterLocator.passwordConfirmEmptyNotification).isVisible(),"Hiển thị thông báo lỗi");
        Assert.assertTrue(page.locator(RegisterLocator.phoneEmptyNotification).isVisible(),"Hiển thị thông báo lỗi");
        Assert.assertTrue(page.locator(RegisterLocator.birthdayEmptyNotification).isVisible(),"Hiển thị thông báo lỗi");
        Assert.assertTrue(page.locator(RegisterLocator.registerBtn).isDisabled(),"Button Register bị vô hiệu hoá");
    }

    @Test
    public void testRegisterWithDuplicatedData() {
        registerPage.register(
                RegisterData.username,
                RegisterData.duplicatedEmail,
                RegisterData.password,
                RegisterData.confirmPassword,
                RegisterData.phone,
                RegisterData.birthday,
                RegisterData.isMale,
                RegisterData.isConfirm
        );

        page.waitForTimeout(2000);
        // Kỳ vọng: không được đăng ký. Nếu có redirect đến profile thì là lỗi.
        boolean isRegistered = page.url().contains("profile") || page.url().contains("dashboard");
        Assert.assertFalse(isRegistered, "Email trùng lặp không được phép đăng ký");
    }

    @Test
    public void testRegisterWithInvalidData() {
        registerPage.register(
                RegisterData.invalidUsername,
                RegisterData.invalidEmail,
                RegisterData.invalidPassword,
                RegisterData.invalidConfirmPassword,
                RegisterData.invalidPhone,
                RegisterData.invalidBirthday,
                RegisterData.isMale,
                RegisterData.isConfirm
        );

        page.waitForTimeout(2000);
        Assert.assertTrue(page.locator(RegisterLocator.registerBtn).isDisabled(),"Button Register bị vô hiệu hoá");
    }

    @Test
    public void testUnseenPasswordAndConfirmPasswordIndependently() {
        // Fill data
        page.fill(RegisterLocator.password, RegisterData.password);
        page.fill(RegisterLocator.confirmPassword, RegisterData.confirmPassword);

        // Ban đầu cả 2 phải là password
        Assert.assertEquals(page.getAttribute(RegisterLocator.password, "type"), "password");
        Assert.assertEquals(page.getAttribute(RegisterLocator.confirmPassword, "type"), "password");

        // Click eye icon của password
        page.click(RegisterLocator.unseenPassBtn);

        // Expected: chỉ password đổi sang text, confirm password vẫn password
        String typePwAfterClick = page.getAttribute(RegisterLocator.password, "type");
        String typeRpwAfterClick = page.getAttribute(RegisterLocator.confirmPassword, "type");

        Assert.assertEquals(typePwAfterClick, "text", "Ô password phải hiển thị sau khi click");
        Assert.assertEquals(typeRpwAfterClick, "password", "Ô confirm password không được thay đổi khi click eye của password");

        // Click eye icon của confirm password
        page.click(RegisterLocator.unseenConfirmPassBtn);

        // Expected: confirm password đổi sang text, password giữ nguyên text
        String typePwFinal = page.getAttribute(RegisterLocator.password, "type");
        String typeRpwFinal = page.getAttribute(RegisterLocator.confirmPassword, "type");

        Assert.assertEquals(typePwFinal, "text", "Ô password giữ nguyên trạng thái hiển thị");
        Assert.assertEquals(typeRpwFinal, "text", "Ô confirm password phải hiển thị sau khi click eye của confirm");
    }
    @Test
    public void testTransferToRegisterPageByUser() {
        loginPage.login(LoginData.email, LoginData.password);
        page.waitForTimeout(5000);
        page.navigate(UrlLocator.JOIN_PAGE);
        Assert.assertFalse(page.locator(LoginLocator.title).isVisible(),"Vẫn truy cập vào trang Login dù đang trong session đăng nhập");
    }
}