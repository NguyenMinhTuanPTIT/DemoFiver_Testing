package Pages;

import Base.BasePage;
import Data.*;
import Locators.*;
import Pages.*;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class HomePage extends BasePage {
    public HomePage(Page page) {
        super(page);
    }

    public String username() {
        return getTextByLocator(HomeLocator.username);
    }

    public boolean transferToPageByClickBtn(String locator, String keyword) {
        clickByLocator(locator);
        page.waitForTimeout(3000);
        if (page.url().contains(keyword)) {
            logger.info("Đã chuyển đến page " + keyword + " thành công");
            return true;
        } else {
            logger.error("Điều chuyển thất bại");
            return false;
        }
    }
    public boolean transferByNavigate(String locator,String keyword) {
        page.mouse().wheel(0, 300);
        String parentLocator = locator + "[contains(text(),'" + keyword + "')]";
        if(page.locator(parentLocator).count()>1) {
            logger.error("Keyword đưa ra bị trùng lặp HOẶC element bị trùng lặp");
            return false;
        }
        if(transferToPageByClickBtn(parentLocator,keyword)) {
            logger.info("Đã điều hướng thành công");
            return true;
        } else {
            return false;
        }
    }

    public boolean transferBySubNavigate(String locator, String keyword, String subkeyword) {
        // scroll xuống một đoạn, không cần điều kiện
        page.mouse().wheel(0, 300);
        String parentLocator = locator + "//p[contains(text(),'" + keyword + "')]";
        hoverByLocator(parentLocator);
        String subLocator = "//p[contains(text(),'" + keyword + "')]" +
                "/ancestor::div[@class='categoriesmenu_li']//div//a[contains(text(),'" + subkeyword + "')]";
        waitForVisibleByLocator(subLocator);
        if (transferToPageByClickBtn(subLocator, subkeyword)) {
            logger.info("Đã điều hướng thành công");
            return true;
        } else {
            return false;
        }
    }
    public boolean logoAction() {
        try {
            // Lưu lại URL và vị trí scroll trước khi click
            page.mouse().wheel(0, 500);
            String oldUrl = page.url();
            int oldScroll = (int) (double) page.evaluate("() => window.scrollY");

            // Click vào logo
            clickByLocator(HomeLocator.logo);

            // Chờ trang ổn định
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Lấy URL và vị trí scroll sau khi click
            String newUrl = page.url();
            int newScroll = (int) (double) page.evaluate("() => window.scrollY");

            logger.info("Old URL: {}, New URL: {}", oldUrl, newUrl);
            logger.info("Old ScrollY: {}, New ScrollY: {}", oldScroll, newScroll);

            // Trường hợp 1: trang reload (URL thay đổi)
            if (!newUrl.equals(oldUrl)) {
                return true;
            }

            // Trường hợp 2: URL giữ nguyên nhưng scroll về đầu trang
            if (newUrl.equals(oldUrl) && newScroll == 0 && oldScroll > 0) {
                return true;
            }

            return false;
        } catch (Exception e) {
            logger.error("Lỗi khi kiểm tra refresh/scroll: {}", e.getMessage(), e);
            return false;
        }
    }


    public boolean switchListProfessionalServices(boolean isLeft) {
        if (isLeft) {
            String beforeAtr = getAttributeOfLocator(HomeLocator.imgList, "style");
            clickByLocator(HomeLocator.previousImgBtn);
            page.waitForTimeout(2000);
            String afterAtr = getAttributeOfLocator(HomeLocator.imgList, "style");
            if (!beforeAtr.equalsIgnoreCase(afterAtr)) {
                logger.info("Đã điều chuyển img thành công");
                return true;
            } else {
                return false;
            }
        } else {
            String beforeAtr = getAttributeOfLocator(HomeLocator.imgList, "style");
            clickByLocator(HomeLocator.nextImgBtn);
            page.waitForTimeout(2000);
            String afterAtr = getAttributeOfLocator(HomeLocator.imgList, "style");
            if (!beforeAtr.equalsIgnoreCase(afterAtr)) {
                logger.info("Đã điều chuyển img thành công");
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean openVideoSingle() {
        clickByLocator(HomeLocator.videoWorldBtn);
        if (page.locator(HomeLocator.videoOnScreen).isVisible()) {
            logger.info("Mở video thành công");
            return true;
        } else {
            return false;
        }
    }


}
