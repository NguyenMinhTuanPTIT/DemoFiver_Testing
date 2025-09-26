package Tests;

import Base.*;
import Data.*;
import Locators.*;
import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ListTest extends BaseTest {
    private HomePage homePage;
    private ListPage listPage;
    private DetailPage detailPage;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(page);
        listPage = new ListPage(page);
        detailPage = new DetailPage(page);
        page.navigate(UrlLocator.HOME_PAGE);
        homePage.waitForVisibleByLocator(HomeLocator.logo);
        homePage.search(HomeLocator.searchHeaderBar, HomeData.keyword);
        page.waitForTimeout(2000);
    }
    @Test
    public void verifyNumberOfProductDisplayed(){
        Assert.assertTrue(listPage.verifyNumberOfResult(),"Số lượng sản phẩm hiển thị không đúng");
    }

    @Test
    public void testFilterBySelectDropdown() {
        Assert.assertTrue(listPage.selectFilter(ListData.filter, ListData.category),"Lọc sản phẩm bằng dropdown thất bại");
    }
    @Test
    public void testFilterBySelectCheckbox() {
        Assert.assertTrue(listPage.selectCheckbox(ListData.checkbox),"Lọc sản phẩm bằng checkbox thất bại");
    }
    @Test
    public void testSortBy() {
        Assert.assertTrue(listPage.selectSort(ListData.sortBy),"Sắp xếp sản phẩm thất bại");
    }
    @Test
    public void testTransferToDetailPage() {
        Assert.assertTrue(listPage.openDetailPage(ListData.productName),"Điều hướng đến trang chi tiết sản phẩm thất bại");
    }
    @Test
    public void verifyLevelSeller() {
        int levelSellerInList = listPage.getLevelSellerInList(ListData.productName);
        listPage.openDetailPage(ListData.productName);
        int levelSellerInDetail = detailPage.getLevelSellerInDetail();
        Assert.assertEquals(levelSellerInList, levelSellerInDetail, "Level Seller không khớp");
    }
}
