package Pages;

import Base.BasePage;
import Data.*;
import Locators.*;
import Pages.*;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.List;

public class ListPage extends BasePage {
    public ListPage(Page page) {
        super(page);
    }

    public int getNumberOfResult() {
        return extractNumber(ListLocator.resultTitle);
    }

    public String getNameOfProduct(String productName) {
        String productLocator = ListLocator.productItem + "//a[contains(text(),'" + productName + "')]";
        int count = page.locator(productLocator).count();
        if (count > 1) {
            logger.error("Tên sản phẩm bị trùng lặp hoặc element bị trùng lặp");
            return null;
        }
        if (count == 1) {
            return getTextByLocator(productLocator);
        }
        if (count == 0) {
            logger.error("Không tìm thấy sản phẩm với tên: " + productName);
            return null;
        }
        return null;
    }

    public int getLevelSellerInList(String productName) {
        String productLevelLocator = "//a[contains(text(),'" + productName + "')]/ancestor::div[@class='col-lg-3 col-md-4 col-sm-6 col-xs-12 mb-4']//div[@class='name']//p";
        page.waitForTimeout(2000);
        String levelText = getTextByLocator(productLevelLocator);
        logger.info(levelText);
        if(levelText == null || levelText.trim().isEmpty()) {
            logger.error("Không tìm thấy level của sản phẩm: " + productName);
            return -1;
        }
        return extractNumber(levelText);
        }


    public int getPriceOfProduct(String productName) {
        String productPriceLocator = "//a[contains(text(),'" + productName + "')]/ancestor::div[@class=\"col-lg-3 col-md-4 col-sm-6 col-xs-12 mb-4\"]//div[@class=\"price d-flex\"]//span";
        String priceText = getTextByLocator(productPriceLocator);
        if (priceText == null || priceText.trim().isEmpty()) {
            logger.error("Không tìm thấy giá của sản phẩm: " + productName);
            return -1;
        } else {
            return extractNumber(priceText);
        }
    }
    public boolean openDetailPage(String productName) {
        String productLocator = ListLocator.productItem + "//a[contains(text(),'" + productName + "')]";
        int count = page.locator(productLocator).count();
        if (count > 1) {
            logger.error("Tên sản phẩm bị trùng lặp hoặc element bị trùng lặp");
            return false;
        }
        if (count == 1) {
            clickByLocator(productLocator);
            page.waitForTimeout(5000);
            Pages.DetailPage detailPage = new Pages.DetailPage(page);
            if(detailPage.nameProduct().contains(productName)) {
                logger.info("Đã chuyển đến trang chi tiết sản phẩm thành công");
                return true;
            } else {
                logger.error("Chuyển đến trang chi tiết sản phẩm thất bại");
                return false;
            }
        }
        if (count == 0) {
            logger.error("Không tìm thấy sản phẩm với tên: " + productName);
            return false;
        }
        return false;
    }

    public boolean verifyNumberOfResult() {
        int displayedResults = page.locator(ListLocator.productItem).count();
        int extractedNumber = getNumberOfResult();
        if (displayedResults == extractedNumber) {
            logger.info("Số lượng result hiển thị khớp với số lượng result đã lọc");
            return true;
        } else {
            logger.error("Số lượng result hiển thị KHÔNG khớp với số lượng result đã lọc");
            return false;
        }
    }

    public boolean selectFilter(String filterName, String subFilterName) {
        int oldNumberOfResult = getNumberOfResult();
        logger.info("Số lượng result ban đầu: " + oldNumberOfResult);
        String parentFilterLocator = ListLocator.filterDropdown + "[contains(text(),'" + filterName + "')]";
        clickByLocator(parentFilterLocator);
        page.waitForTimeout(2000);
        String parentSubFilterLocator = ListLocator.filterItemList + "[contains(text(),'" + subFilterName + "')]";
        selectItemInList(parentSubFilterLocator, subFilterName);
        page.waitForTimeout(3000);
        int newNumberOfResult = getNumberOfResult();
        logger.info("Số lượng result sau khi lọc: " + newNumberOfResult);
        if (oldNumberOfResult != newNumberOfResult) {
            logger.info("Bộ lọc hoạt động hiệu quả");
            return true;
        }
        return false;
    }

    public boolean selectCheckbox(@org.jetbrains.annotations.NotNull String checkboxName) {
        if (!checkboxName.equalsIgnoreCase("Pro services") &&
                !checkboxName.equalsIgnoreCase("Local sellers") &&
                !checkboxName.equalsIgnoreCase("Online Sellers")) {
            logger.error("Tên checkbox không hợp lệ: " + checkboxName);
            return false;
        }
        int oldNumberOfResult = getNumberOfResult();
        logger.info("Số lượng result ban đầu: " + oldNumberOfResult);
        String parentCheckboxLocator = ListLocator.filterCheckbox + "[contains(text(),'" + checkboxName + "')]/..//input";
        clickByLocator(parentCheckboxLocator);
        page.waitForTimeout(3000);
        int newNumberOfResult = getNumberOfResult();
        logger.info("Số lượng result sau khi lọc: " + newNumberOfResult);
        if (oldNumberOfResult != newNumberOfResult) {
            logger.info("Bộ lọc hoạt động hiệu quả");
            return true;
        }
        return false;
    }

    public boolean selectSort(String sortName) {
        if (!sortName.equalsIgnoreCase("Relevance") &&
                !sortName.equalsIgnoreCase("Best Selling") &&
                !sortName.equalsIgnoreCase("New Arrivals")) {
            logger.error("Tên sort không hợp lệ: " + sortName);
            return false;
        }
        List<String> oldList = getTextsByLocator(ListLocator.productItem + "a");
        clickByLocator(ListLocator.sortDropdown);
        page.locator(ListLocator.sortDropdown).selectOption(sortName);
        page.waitForTimeout(2000);
        List<String> newList = getTextsByLocator(ListLocator.productItem + "a");
        if (!oldList.equals(newList)) {
            logger.info("Chức năng sắp xếp hoạt động hiệu quả");
            return true;
        }
        return false;
    }

}
