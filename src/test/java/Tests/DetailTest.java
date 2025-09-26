package Tests;

import Base.*;
import Pages.LoginPage;
import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.DetailPage;
import Locators.*;
import Data.*;

public class DetailTest extends BaseTest {
    private DetailPage detailPage;
    private ProfilePage profilePage;

    @BeforeMethod
    public void init() {
        detailPage = new DetailPage(page);
        profilePage = new ProfilePage(page);
    }

    @Test
    public void testBuyProductWithUserAccount() {
        detailPage.buyProduct("Basic",true);
        String nameProduct =detailPage.nameProduct();
        Assert.assertTrue(profilePage.isProductExist(nameProduct),"mua hàng thất bại");
    }

    @Test
    public void testBuyProductWithGuestAccount() {
        detailPage.buyProduct("Basic",false);
        String nameProduct =detailPage.nameProduct();
        Assert.assertTrue(profilePage.isProductExist(nameProduct),"mua hàng thất bại");
    }

    @Test
    public void testBuyProductMutipleTimes() {
        detailPage.buyProduct("Basic",true);
        detailPage.clickByLocator(DetailLocator.continuteButton);
        detailPage.clickByLocator(DetailLocator.continuteButton);
        detailPage.clickByLocator(DetailLocator.continuteButton);
        detailPage.clickByLocator(DetailLocator.continuteButton);
        String nameProduct =detailPage.nameProduct();
        Assert.assertTrue(profilePage.isProductExist(nameProduct),"mua hàng thất bại");
    }

    @Test
    public void testAmountRating() {
        Assert.assertTrue(detailPage.verifyAmountRating(),"số lượng rating hiển thị không đồng nhất");
    }

    //test case failed
    @Test
    public void testTransferToCompareProductPage() {
        Assert.assertTrue(detailPage.compareProduct(),"Điều hướng thất bại");
    }

    @Test
    public void testRateYesToOtherComment() {
        Assert.assertTrue(detailPage.evaluateComment(DetailData.author,DetailData.authorcomment,true,true),"Chức năng đánh giá comment người khác hoạt động sai");
    }

    @Test
    public void testRateNoToOtherComment() {
        Assert.assertTrue(detailPage.evaluateComment(DetailData.author,DetailData.authorcomment,false,true),"Chức năng đánh giá comment người khác hoạt động sai");
    }

    @Test
    public void testTransferToContactSellerPage() {
        Assert.assertTrue(detailPage.contactSeller(true));
    }

    @Test
    public void testCommentWithUserAccount() {
        Assert.assertTrue(detailPage.comment(RegisterData.username,DetailData.think,DetailData.ratingStar,true));
    }

    @Test
    public void testCommentWithGuestAccount() {
        Assert.assertTrue(detailPage.comment(RegisterData.username,DetailData.think,DetailData.ratingStar,false));
    }

    @Test
    public void testFAQLogic() {
        Assert.assertTrue(detailPage.verifyFaqLogic());
    }

    @Test
    public void testSortCommentByOptionRecent() {
        Assert.assertTrue(detailPage.sortCommentByDropdown("recent"));
    }

    @Test
    public void testSortCommentByOptionRelevant() {
        Assert.assertTrue(detailPage.sortCommentByDropdown("revelant"));
    }

    @Test
    public void testSortCommentByFilter() {
        Assert.assertTrue(detailPage.sortCommentByFilter("hảo hảo"));
    }

    @Test
    public void testOrderCountAfterBuy() {
        page.navigate(UrlLocator.DETAIL_PAGE);
        int countBefore = detailPage.extractNumber(detailPage.getTextByLocator(DetailLocator.orderInQueue));
        logger.info(countBefore);
        detailPage.buyProduct("Basic", true);
        page.waitForTimeout(5000);
        page.reload();
        page.waitForTimeout(3000);
        int countAfter = detailPage.extractNumber(detailPage.getTextByLocator(DetailLocator.orderInQueue));
        logger.info(countAfter);
        Assert.assertTrue(countAfter > countBefore, "hệ thống thống kê đơn hàng không hoạt động");

    }

    @Test
    public void testOrderCountAfterDelete() {
        page.navigate(UrlLocator.DETAIL_PAGE);
        int countBefore = detailPage.extractNumber(detailPage.getTextByLocator(DetailLocator.orderInQueue));
        logger.info(countBefore);
        detailPage.buyProduct("Basic", true);
        page.waitForTimeout(5000);
        page.navigate(UrlLocator.PROFILE_PAGE);
        page.waitForTimeout(4000);
        profilePage.deleteProductByName(DetailData.productName);
        page.navigate(UrlLocator.DETAIL_PAGE);
        int countAfter = detailPage.extractNumber(detailPage.getTextByLocator(DetailLocator.orderInQueue));
        logger.info(countAfter);
        Assert.assertTrue(countAfter > countBefore, "hệ thống thống kê đơn hàng không hoạt động");
    }

}
