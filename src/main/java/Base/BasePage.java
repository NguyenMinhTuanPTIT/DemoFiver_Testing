package Base;

import Locators.HomeLocator;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasePage {
    protected final Page page;
    protected final Logger logger = LogManager.getLogger(this.getClass());

    public BasePage(Page page) {
        this.page = page;
    }

    // Click (chờ visible trước khi click)
    public void clickByLocator(String locator) {
        try {
            waitForVisibleByLocator(locator);
            page.locator(locator).click();
            logger.info("Đã click vào element: {}", locator);
        } catch (Exception e) {
            logger.error("Lỗi khi click vào element: {}", locator, e);
            throw e;
        }
    }

    // Nhập text (chờ visible)
    public void typeByLocator(String locator, String text) {
        try {
            waitForVisibleByLocator(locator);
            page.locator(locator).type(text);
            logger.info("Đã nhập '{}' vào element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Lỗi khi nhập vào element: {}", locator, e);
            throw e;
        }
    }
    public void fillByLocator(String locator, String text) {
        try {
            waitForVisibleByLocator(locator);
            page.locator(locator).fill(text);
            logger.info("Đã nhập '{}' vào element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Lỗi khi nhập vào element: {}", locator, e);
            throw e;
        }
    }

    // Lấy text
    public String getTextByLocator(String locator) {
        try {
            waitForVisibleByLocator(locator);
            String text = page.locator(locator).textContent();
            logger.info("Lấy text từ element {} = '{}'", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy text từ element: {}", locator, e);
            throw e;
        }
    }

    // Lấy value của input
    public String getInputValueByLocator(String locator) {
        try {
            waitForVisibleByLocator(locator);
            String value = page.locator(locator).inputValue();
            logger.info("Lấy value từ input {} = '{}'", locator, value);
            return value;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy value từ input: {}", locator, e);
            throw e;
        }
    }

    public List<String> getTextsByLocator(String locator) {
        try {
            List<String> texts = page.locator(locator).allTextContents();
            logger.info("Đã lấy thành công danh sách text từ locator: {}", locator);
            return texts;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách text từ locator: {}", locator, e);
            throw e;
        }
    }
    public void selectItemInList( String locator,String name) {
        try {
            Locator items = page.locator(locator);
            int count = items.count();

            for (int i = 0; i < count; i++) {
                String text = items.nth(i).innerText().trim();
                if (text.equalsIgnoreCase(name)) {
                    items.nth(i).click();
                    logger.info("Đã chọn item '{}' trong danh sách với locator: {}", name, locator);
                    return;
                }
            }

            logger.warn("Không tìm thấy item '{}' trong danh sách với locator: {}", name, locator);
            throw new RuntimeException("Item '" + name + "' không tồn tại trong danh sách");
        } catch (Exception e) {
            logger.error("Lỗi khi chọn item '{}' trong danh sách với locator: {}", name, locator, e);
            throw e;
        }
    }


    public int extractNumber(String text) {
        // Biểu thức chính quy để tìm một hoặc nhiều chữ số liên tiếp
        try {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
            return -1;
        } catch (Exception e) {
            logger.error("Lỗi trong quá trình lấy số: " + e);
            return 0;
        }
        // Trả về -1 hoặc ném ngoại lệ nếu không tìm thấy số
    }

    // Kiểm tra hiển thị
    public boolean isVisibleByLocator(String locator) {
        try {
            boolean visible = page.locator(locator).isVisible();
            logger.info("Element {} hiển thị: {}", locator, visible);
            return visible;
        } catch (Exception e) {
            logger.error("Lỗi khi kiểm tra hiển thị của element: {}", locator, e);
            throw e;
        }
    }

    public boolean isErrorVisible(String locator) {
        return isVisibleByLocator(locator);
    }

    // Kiểm tra radio/checkbox checked state
    public boolean isCheckedByLocator(String locator) {
        try {
            waitForVisibleByLocator(locator);
            boolean checked = page.locator(locator).isChecked();
            logger.info("Element {} checked: {}", locator, checked);
            return checked;
        } catch (Exception e) {
            logger.error("Lỗi khi kiểm tra checked của element: {}", locator, e);
            throw e;
        }
    }

    // Di chuột
    public void hoverByLocator(String locator) {
        try {
            waitForVisibleByLocator(locator);
            page.locator(locator).hover();
            logger.info("Đã hover vào element: {}", locator);
        } catch (Exception e) {
            logger.error("Lỗi khi hover vào element: {}", locator, e);
            throw e;
        }
    }
    public boolean search(String locator, String keyword) {
        try {
            // Xác định loại search bar
            if (locator.equalsIgnoreCase(HomeLocator.searchBodyBar)) {
                logger.info("Thực hiện search qua BODY search bar");
            } else if (locator.equalsIgnoreCase(HomeLocator.searchHeaderBar)) {
                logger.info("Thực hiện search qua HEADER search bar");
            } else {
                logger.warn("Locator không khớp body/header: {}", locator);
                return false;
            }

            // Điền từ khóa và submit
            fillByLocator(locator, keyword);
            page.keyboard().press("Enter");

            // Đợi kết quả
            page.waitForTimeout(5000);
            String currentUrl = page.url();
            logger.info("URL sau khi search: {}", currentUrl);

            // Kiểm tra URL kết quả
            if (currentUrl.contains(keyword) && currentUrl.contains("result")) {
                logger.info("Đã search thành công với từ khóa: {}", keyword);
                return true;
            } else {
                logger.warn("Search thất bại hoặc URL không hợp lệ với từ khóa: {}", keyword);
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi search với từ khóa {}: {}", keyword, e.getMessage(), e);
            return false;
        }
    }


    // Chờ element visible
    public void waitForVisibleByLocator(String locator) {
        try {
            page.locator(locator)
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            logger.info("Đã chờ element {} hiển thị", locator);
        } catch (Exception e) {
            logger.error("Lỗi khi chờ element hiển thị: {}", locator, e);
            throw e;
        }
    }

    // Chờ element hidden
    public void waitForHiddenByLocator(String locator) {
        try {
            page.locator(locator)
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
            logger.info("Đã chờ element {} ẩn", locator);
        } catch (Exception e) {
            logger.error("Lỗi khi chờ element ẩn: {}", locator, e);
            throw e;
        }
    }

    public String getAttributeOfLocator(String locator, String attributeName) {
        try {
            waitForVisibleByLocator(locator);
            String value = page.locator(locator).getAttribute(attributeName);
            logger.info("Lấy attribute '{}' từ locator {} = '{}'", attributeName, locator, value);
            return value;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy attribute '{}' từ locator {}: {}", attributeName, locator, e.getMessage());
            throw e;
        }
    }
}
