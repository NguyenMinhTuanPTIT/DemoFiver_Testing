package Pages;

import Base.BasePage;
import Locators.UrlLocator;
import com.microsoft.playwright.*;
import Locators.ProfileLocator;
import com.microsoft.playwright.options.LoadState;

import java.util.Optional;

public class ProfilePage extends BasePage {
    public ProfilePage(Page page) {
        super(page);
    }

    // Hàm update profile, trả về true nếu profile được update, false nếu nhấn cancel hoặc không thay đổi
    public boolean updateProfile(
            Optional<String> newPhone,
            Optional<String> newName,
            Optional<String> newBirthday,
            Optional<Boolean> isMale,
            Optional<String> certificate,
            Optional<String> skill,
            Optional<Boolean> isUpdate
    ) {
        clickByLocator(ProfileLocator.editProfile);
        waitForVisibleByLocator(ProfileLocator.titlePodup);

        boolean hasUpdate = false;

        if (newPhone.isPresent()) {
            typeByLocator(ProfileLocator.editPhoneNumber, newPhone.get());
            hasUpdate = true;
        }
        if (newName.isPresent()) {
            typeByLocator(ProfileLocator.editName, newName.get());
            hasUpdate = true;
        }
        if (newBirthday.isPresent()) {
            typeByLocator(ProfileLocator.editBirthday, newBirthday.get());
            hasUpdate = true;
        }
        if (isMale.isPresent()) {
            clickByLocator(isMale.get() ? ProfileLocator.optionMales : ProfileLocator.optionFemales);
            hasUpdate = true;
        }
        if (certificate.isPresent()) {
            typeByLocator(ProfileLocator.editCertificate, certificate.get());
            hasUpdate = true;
        }
        if (skill.isPresent()) {
            typeByLocator(ProfileLocator.editSkill, skill.get());
            hasUpdate = true;
        }

        if (hasUpdate && isUpdate.orElse(true)) {
            clickByLocator(ProfileLocator.btnSave);
            // chờ load xong sau khi save
            page.waitForLoadState(LoadState.NETWORKIDLE);
            return true;
        } else {
            clickByLocator(ProfileLocator.btnCancel);
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            return false;
        }
    }

    /**
     * Verify only the fields provided in the Optional parameters.
     * Trả về true nếu tất cả field được truyền vào khớp với giá trị trên UI.
     */
    public boolean verifyValueUpdated(
            Optional<String> expectedPhone,
            Optional<String> expectedName,
            Optional<String> expectedBirthday,
            Optional<Boolean> expectedIsMale,
            Optional<String> expectedCertificate,
            Optional<String> expectedSkill
    ) {
        // Mở lại Edit Profile để kiểm tra
        clickByLocator(ProfileLocator.editProfile);
        waitForVisibleByLocator(ProfileLocator.titlePodup);

        boolean isCorrect = true;

        if (expectedPhone.isPresent()) {
            String actualPhone = getInputValueByLocator(ProfileLocator.editPhoneNumber);
            if (!actualPhone.equals(expectedPhone.get())) {
                logger.error("❌ Phone không đúng. Expected: {} | Actual: {}", expectedPhone.get(), actualPhone);
                isCorrect = false;
            }
        }

        if (expectedName.isPresent()) {
            String actualName = getInputValueByLocator(ProfileLocator.editName);
            if (!actualName.equals(expectedName.get())) {
                logger.error("❌ Name không đúng. Expected: {} | Actual: {}", expectedName.get(), actualName);
                isCorrect = false;
            }
        }

        if (expectedBirthday.isPresent()) {
            String actualBirthday = getInputValueByLocator(ProfileLocator.editBirthday);
            if (!actualBirthday.equals(expectedBirthday.get())) {
                logger.error("❌ Birthday không đúng. Expected: {} | Actual: {}", expectedBirthday.get(), actualBirthday);
                isCorrect = false;
            }
        }

        if (expectedIsMale.isPresent()) {
            boolean actualIsMale = isCheckedByLocator(ProfileLocator.optionMales);
            if (actualIsMale != expectedIsMale.get()) {
                logger.error("❌ Giới tính không đúng. Expected: {} | Actual: {}", expectedIsMale.get(), actualIsMale);
                isCorrect = false;
            }
        }

        if (expectedCertificate.isPresent()) {
            String actualCertificate = getInputValueByLocator(ProfileLocator.editCertificate);
            if (!actualCertificate.equals(expectedCertificate.get())) {
                logger.error("❌ Certificate không đúng. Expected: {} | Actual: {}", expectedCertificate.get(), actualCertificate);
                isCorrect = false;
            }
        }

        if (expectedSkill.isPresent()) {
            String actualSkill = getInputValueByLocator(ProfileLocator.editSkill);
            if (!actualSkill.equals(expectedSkill.get())) {
                logger.error("❌ Skill không đúng. Expected: {} | Actual: {}", expectedSkill.get(), actualSkill);
                isCorrect = false;
            }
        }

        // Đóng popup sau khi verify
        clickByLocator(ProfileLocator.btnCancel);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        return isCorrect;
    }

    // Hàm click social link và trả về URL mới
    public String clickSocialLinkAndGetUrl(String locator) {
        waitForVisibleByLocator(locator);
        String currentUrl = page.url();
        clickByLocator(locator);
        // chờ chuyển trang hoặc load nội dung mới
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.url();
    }

    // Hàm click CreateNewGig
    public boolean transferToCreateNewGigPage() {
        clickByLocator(ProfileLocator.createNewGigBtn);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.url().contains("gig");
    }

    public boolean isProductExist(String containsName) {
        // Lấy tất cả product item
        page.navigate(UrlLocator.PROFILE_PAGE);
        page.waitForTimeout(3000);
        Locator productItems = page.locator(ProfileLocator.productItems);
        page.waitForTimeout(2000);
        int count = productItems.count();
        int countProduct = 0;

        if (count == 0) {
            logger.warn("Danh sách Product đang trống");
            return false;
        }

        for (int i = 0; i < count; i++) {
            String productSelector = "(" + ProfileLocator.productItems + "//h1)[" + (i + 1) + "]";
            String productName = getTextByLocator(productSelector);

            logger.info("Kiểm tra sản phẩm thứ " + (i + 1) + ": '" + productName + "'");

            if (productName != null && productName.contains(containsName)) {
                logger.info("Đã tìm thấy sản phẩm khớp: " + productName);
                countProduct++;
            }
        }
        if(countProduct>=2) {
            logger.error("Có hơn 2 sản phẩm giống nhau cùng xuất hiện trong giỏ hàng");
            return false;
        }else if(countProduct ==0) {
            logger.warn("Không tìm thấy sản phẩm nào chứa chuỗi: '" + containsName + "'");
            return false;
        } else{
            return true;
        }


    }

    public boolean deleteProductByName(String containName) {
        page.waitForTimeout(4000);
        Locator productItems = page.locator(ProfileLocator.productItems);
        page.waitForTimeout(2000);
        int beforeAmount = productItems.count();
        if (isProductExist(containName)) {
            String deleteProductLocator = "//div[@class='gigs_card']//h1[contains(text(),'" + containName + "')]/ancestor::div[@class='gigs_card']//button[@class='delete']";
            page.locator(deleteProductLocator).click();
        } else {
            return false;
        }
        page.waitForTimeout(4000);
        int afterAmount = productItems.count();
        if (beforeAmount > afterAmount) {
            logger.info("Đã xoá thành công");
            return true;
        } else {
            logger.warn("Xoá thất bại");
            return false;
        }
    }

    public boolean transferToDetaiPage(String containName) {
        if (isProductExist(containName)) {
            String intoDetailProductLocator = "//div[@class='gigs_card']//h1[contains(text(),'"+containName+"')]/ancestor::div[@class='gigs_card']//button[@class=\"viewdetail\"]//a";
            clickByLocator(intoDetailProductLocator);
            page.waitForTimeout(4000);
            if(page.url().contains("jobDetail")) {
                logger.info("Đã điều hướng thành công");
                return true;
            }else {
                logger.error("Đã điều hướng thất bại");
                return false;
            }
        }else {
            return false;
        }
    }
}
