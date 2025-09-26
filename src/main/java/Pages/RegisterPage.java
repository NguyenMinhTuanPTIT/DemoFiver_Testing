// File: src/main/java/Pages/RegisterPage.java
package Pages;

import Base.BasePage;
import com.microsoft.playwright.Page;
import Locators.*;

public class RegisterPage extends BasePage {
    public RegisterPage(Page page) {
        super(page);
    }

    public void register(String name, String email, String password, String confirmPass, String phone, String birthday, boolean isMale, boolean isConfirm) {
        page.navigate(UrlLocator.JOIN_PAGE);
        typeByLocator(RegisterLocator.username, name);
        typeByLocator(RegisterLocator.email, email);
        typeByLocator(RegisterLocator.password, password);
        typeByLocator(RegisterLocator.confirmPassword, confirmPass);
        typeByLocator(RegisterLocator.phone, phone);

        // === Birthday field debug ===
        try {
            clickByLocator(RegisterLocator.birthday);
            page.waitForTimeout(1000);

            // Một số date picker không cho nhập từng phần. Thử nhập toàn bộ chuỗi ddmmyyyy.
            page.locator(RegisterLocator.birthday).type(birthday);
            String actual = page.inputValue(RegisterLocator.birthday);
            if (!birthday.equals(actual)) {
                logger.warn("[Bug] Birthday input không nhận giá trị. Expected=" + birthday + " | Actual=" + actual);
            } else {
                logger.info("Birthday input set thành công: " + actual);
            }
        } catch (Exception e) {
            logger.error("[Bug] Không thể thao tác với birthday input. Locator=" + RegisterLocator.birthday + " | Error=" + e.getMessage());
            // không throw để test vẫn có thể tiếp tục kiểm tra các phần khác nếu cần
        }

        if (isMale) {
            clickByLocator(RegisterLocator.opionMale);
        } else {
            clickByLocator(RegisterLocator.opionFemale);
        }
        if (isConfirm) {
            clickByLocator(RegisterLocator.confirmAgreeBtn);
        }
        clickByLocator(RegisterLocator.registerBtn);
    }


    public void transferToLoginPage() {
        page.navigate(UrlLocator.JOIN_PAGE);
        clickByLocator(RegisterLocator.transferToLoginBtn);
    }
}