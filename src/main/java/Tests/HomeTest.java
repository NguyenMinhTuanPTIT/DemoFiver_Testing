package Tests;

import Base.BaseTest;
import Data.HomeData;
import Data.LoginData;
import Locators.HomeLocator;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    private HomePage homePage;
    private LoginPage loginPage;
    @BeforeMethod
    public void init() {
        loginPage = new LoginPage(page);
        homePage = new HomePage(page);
        page.navigate("https://demo5.cybersoft.edu.vn/");
        homePage.waitForVisibleByLocator(HomeLocator.logo);
        page.waitForTimeout(2000);
    }
    @Test
    public void testTransferToFiverrBusinessPage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.fiverBusinessBtn,"fiverr"),"Điều hướng đến trang Fiverr Business thất bại");
    }
    @Test
    public void testTransferToExplorePage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.exploreBtn,"explore"),"Điều hướng đến trang khám phá thất bại");
    }
    @Test
    public void testTransferToLanguageSettingPage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.changeLanguageBtn,"language"),"Chức năng đổi ngôn ngữ không hoạt động");
    }
    @Test
    public void testTransferToCurrencyPage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.changeCurrencyBtn,"currency"),"Chức năng đổi tiền tệ không hoạt động");

    }
    @Test
    public void testTransferToBecomeSellerPage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.becomeSellerBtn,"sell"),"Điều hướng tới trang Become Seller thất bại");
    }
    @Test
    public void testTransferToProfilePage() {
        homePage.clickByLocator(HomeLocator.loginBtn);
        loginPage.login(LoginData.email,LoginData.password);
        loginPage.clickByLocator(HomeLocator.logo);
        page.waitForTimeout(4000);
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.profileBtn,"profile"),"Điều hướng đến trang Profile thất bại");
    }
    @Test
    public void testRefreshPage() {
        Assert.assertTrue(homePage.logoAction(),"Chức năng làm mới trang hoặc kéo lên đầu trang khi click Logo không hoạt động");
    }
    @Test
    public void testTransferToLoginPage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.loginBtn,"login"),"Điều hướng đến trang Login thất bại");
    }
    @Test
    public void testTransferToRegisterPage() {
        Assert.assertTrue(homePage.transferToPageByClickBtn(HomeLocator.joinBtn,"register"),"Điều hướng đến trang Register thất bại");
    }
    @Test
    public void testTransferToListPageByParentHeaderNavigate() {
        Assert.assertTrue(homePage.transferByNavigate(HomeLocator.navigateHeaderItems, HomeData.navigateKeyword));
    }
    @Test
    public void testTransferToListPageBySubHeaderNavigate() {
        Assert.assertTrue(homePage.transferBySubNavigate(HomeLocator.navigateHeaderItems,HomeData.navigateKeyword,HomeData.navigateSubKeyword));
    }
    @Test
    public void testTransferToPopularCategoriesPage() {
        Assert.assertTrue(homePage.transferByNavigate(HomeLocator.navigateBodyItems,HomeData.popularCategory));
    }
    @Test
    public void testBodySearch() {
        Assert.assertTrue(homePage.search(HomeLocator.searchBodyBar,HomeData.keyword),"Chức năng tìm kiếm không hoạt động");
    }
    @Test
    public void testSwitchListImageToRight() {
        Assert.assertTrue(homePage.switchListProfessionalServices(false),"danh sách ảnh không thay đổi khi chuyển về bên Phải");
    }
    @Test
    public void testSwitchListImageToLeft() {
        Assert.assertTrue(homePage.switchListProfessionalServices(true),"danh sách ảnh không thay đổi khi chuyển về bên Trái");
    }
    @Test
    public void testOpenVideoSingle() {
        Assert.assertTrue(homePage.openVideoSingle(),"Video Single không mở được");
    }
    @Test
    public void testTransferByExploreMarket() {
        Assert.assertTrue(homePage.transferByNavigate(HomeLocator.categoryList,HomeData.exporeCategory),"Keyword không tồn tại");
    }

}
