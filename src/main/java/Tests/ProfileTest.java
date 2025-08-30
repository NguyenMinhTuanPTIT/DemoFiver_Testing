package Tests;

import Base.BaseTest;
import Data.LoginData;
import Data.ProfileData;
import Locators.ProfileLocator;
import Locators.UrlLocator;
import Pages.LoginPage;
import Pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Optional;

public class ProfileTest extends BaseTest {
    private ProfilePage profilePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void init() {
        profilePage = new ProfilePage(page);
        loginPage = new LoginPage(page);

        // Login
        page.navigate(UrlLocator.LOGIN_URL);
        loginPage.login(LoginData.email, LoginData.password);

        // Chờ chắc chắn profile page load
        page.navigate(UrlLocator.profilePageUrl);
        profilePage.waitForVisibleByLocator(ProfileLocator.editProfile);
        logger.info("✅ Đã login và điều hướng đến trang Profile");
    }

    /**
     * Helper: Verify giá trị trong form sau khi update
     */
    private void verifyProfileFields() {
        if (ProfileData.newPhone.isPresent()) {
            String actual = profilePage.getInputValueByLocator(ProfileLocator.editPhoneNumber);
            Assert.assertEquals(actual, ProfileData.newPhone.get(), "❌ Phone không khớp");
        }
        if (ProfileData.newUsername.isPresent()) {
            String actual = profilePage.getInputValueByLocator(ProfileLocator.editName);
            Assert.assertEquals(actual, ProfileData.newUsername.get(), "❌ Username không khớp");
        }
        if (ProfileData.newBirthday.isPresent()) {
            String actual = profilePage.getInputValueByLocator(ProfileLocator.editBirthday);
            Assert.assertEquals(actual, ProfileData.newBirthday.get(), "❌ Birthday không khớp");
        }
        if (ProfileData.newCertificate.isPresent()) {
            String actual = profilePage.getInputValueByLocator(ProfileLocator.editCertificate);
            Assert.assertEquals(actual, ProfileData.newCertificate.get(), "❌ Certificate không khớp");
        }
        if (ProfileData.newSkill.isPresent()) {
            String actual = profilePage.getInputValueByLocator(ProfileLocator.editSkill);
            Assert.assertEquals(actual, ProfileData.newSkill.get(), "❌ Skill không khớp");
        }
        logger.info("✅ Đã verify toàn bộ field cần thiết");
    }

    /**
     * Test cập nhật profile và verify lại dữ liệu
     */
    @Test
    public void testUpdateProfile() {
        boolean updated = profilePage.updateProfile(
                ProfileData.newPhone,
                ProfileData.newUsername,
                ProfileData.newBirthday,
                ProfileData.isMale,
                ProfileData.newCertificate,
                ProfileData.newSkill,
                ProfileData.isUpdate
        );

        Assert.assertTrue(updated, "❌ Profile không được update");
        logger.info("✅ Profile đã được update thành công");

        // Verify các field đã nhập
        verifyProfileFields();
    }

    /**
     * Test nhấn cancel, dữ liệu không bị lưu
     */
    @Test
    public void testCancelUpdateProfile() {
        boolean updated = profilePage.updateProfile(
                ProfileData.newPhone,
                ProfileData.newUsername,
                ProfileData.newBirthday,
                ProfileData.isMale,
                ProfileData.newCertificate,
                ProfileData.newSkill,
                Optional.of(false) // nhấn cancel
        );

        Assert.assertFalse(updated, "❌ Profile vẫn bị update khi nhấn cancel");
        logger.info("✅ Cancel update hoạt động chính xác, dữ liệu không thay đổi");
    }

    /**
     * Test navigation đến social link
     */
    @Test
    public void testSocialLinkNavigationByKeyword() {
        String socialNetwork = ProfileData.socialNetwork.toLowerCase();

        // Map từ khóa → locator
        Map<String, String> socialLocators = Map.of(
                "facebook", ProfileLocator.transferToFacebook,
                "google", ProfileLocator.transferToGoogle,
                "github", ProfileLocator.transferToGithub,
                "twitter", ProfileLocator.transferToTwitter,
                "dribbble", ProfileLocator.transferToDirbble,
                "stackoverflow", ProfileLocator.transferToStackOverFlow
        );

        Assert.assertTrue(socialLocators.containsKey(socialNetwork),
                "❌ Từ khóa socialNetwork không hợp lệ: " + socialNetwork);

        String locator = socialLocators.get(socialNetwork);

        String newUrl = profilePage.clickSocialLinkAndGetUrl(locator);

        Assert.assertTrue(newUrl.toLowerCase().contains(socialNetwork),
                "❌ Truy cập thất bại: URL " + newUrl + " không chứa từ khóa '" + socialNetwork + "'");
        logger.info("✅ Đã điều hướng thành công tới " + socialNetwork);
    }

    /**
     * Test điều hướng sang trang Create New Gig
     */
    @Test
    public void testTransferToCreateNewGig() {
        Assert.assertTrue(profilePage.transferToCreateNewGigPage(),
                "❌ Không thể điều hướng tới trang Create New Gig");
        logger.info("✅ Đã điều hướng tới trang Create New Gig thành công");
    }

    @Test
    public void testExistProduct() {
        Assert.assertTrue(profilePage.isProductExist(ProfileData.containName),"Không thể tìm thấy");
    }

    @Test
    public void testDeleteProduct() {
        Assert.assertTrue(profilePage.deleteProductByName(ProfileData.containName),"Đã xoá thất bại");
    }

    @Test
    public void testTransferToDetailPage() {
        Assert.assertTrue(profilePage.transferToDetaiPage(ProfileData.containName),"Truy cập detail page thất bại");
    }
}
