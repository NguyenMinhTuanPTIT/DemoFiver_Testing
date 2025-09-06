package Pages;

import Base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Data.*;
import Locators.*;
import org.bouncycastle.oer.its.etsi102941.Url;
import Pages.ProfilePage;
import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailPage extends BasePage {
    public DetailPage(Page page) {
        super(page);
    }

    public String nameProduct() {
        return getTextByLocator(DetailLocator.detailTitle);
    }
    public boolean chooseAccountStatus(boolean status) {
        try {
            if (status) {
                page.navigate(UrlLocator.LOGIN_URL);
                LoginPage loginPage = new LoginPage(page);
                loginPage.login(LoginData.email, LoginData.password);
                page.navigate(UrlLocator.detailPageUrl);
                page.waitForTimeout(5000);
            } else {
                page.navigate(UrlLocator.detailPageUrl);
                page.waitForTimeout(5000);
            }
            logger.info("Đã chọn trạng thái tài khoản: " + (status ? "Login" : "Guest"));
            return true;
        } catch (Exception e) {
            logger.error("Lỗi khi chọn trạng thái tài khoản", e);
            return false;
        }
    }

    public boolean chooseStar(double expectStar) {
        try {
            if (expectStar < 0.5 || expectStar > 5 || Math.abs(expectStar % 0.5) > 1e-9) {
                throw new IllegalArgumentException("Số sao phải từ 0.5 đến 5.0 và chia hết cho 0.5");
            }
            int key = (int) (expectStar * 10);

            switch (key) {
                case 5:
                    clickByLocator(DetailLocator.halfRatingStar);
                    break;
                case 10:
                    clickByLocator(DetailLocator.oneRatingStar);
                    break;
                case 15:
                    clickByLocator(DetailLocator.onehalfRatingStar);
                    break;
                case 20:
                    clickByLocator(DetailLocator.twoRatingStar);
                    break;
                case 25:
                    clickByLocator(DetailLocator.twohalfRatingStar);
                    break;
                case 30:
                    clickByLocator(DetailLocator.threeRatingStar);
                    break;
                case 35:
                    clickByLocator(DetailLocator.threehalfRatingStar);
                    break;
                case 40:
                    clickByLocator(DetailLocator.fourRatingStar);
                    break;
                case 45:
                    clickByLocator(DetailLocator.fourhalfRatingStar);
                    break;
                case 50:
                    clickByLocator(DetailLocator.fiveRatingStar);
                    break;
                default:
                    throw new IllegalStateException("Không tìm thấy locator cho số sao " + expectStar);
            }

            logger.info("Đã chọn số sao: " + expectStar);
            return true;
        } catch (Exception e) {
            logger.error("Lỗi khi chọn số sao", e);
            return false;
        }
    }

    public boolean isCommentExist(String author, String comment) {
        this.chooseAccountStatus(true);
        page.waitForTimeout(4000);
        int count = page.locator(DetailLocator.commentItem).count();
        if (count == 0) {
            logger.warn("Không có comment trong danh sách");
            return false;
        }

        for (int i = 0; i < count; i++) {
            String commentSelector = "//li[@class='row py-4'][" + (i + 1) + "]";
            String authorName = getTextByLocator(commentSelector + "//h3");
            String commentName = getTextByLocator(commentSelector + "//p");

            logger.info("Kiểm tra comment thứ " + (i + 1) + ": '" + authorName + ": " + commentName + "'");

            if (authorName != null && commentName != null
                    && authorName.equalsIgnoreCase(author)
                    && commentName.toLowerCase().contains(comment.toLowerCase())) {
                logger.info("Đã tìm thấy comment chứa chuỗi khớp: " + authorName + " - " + commentName);
                return true;
            }
        }

        logger.warn("Không tìm thấy comment nào có tác giả '" + author + "' và chứa chuỗi '" + comment + "'");
        return false;
    }

    public boolean compareProduct() {
        this.chooseAccountStatus(true);
        page.navigate(UrlLocator.detailPageUrl);
        clickByLocator(DetailLocator.compareButton);
        page.waitForTimeout(5000);
        if (page.url().contains("compare")) {
            logger.info("đã điều hướng thành công");
            return true;
        } else {
            logger.error("điều hướng thất bại");
            return false;
        }
    }

    public boolean evaluateComment(String author, String comment, boolean isHelpful, boolean status) {
        this.chooseAccountStatus(status);

        if (!status) {
            logger.error("Không thể đánh giá comment bằng tài khoản guest");
            return false;
        }

        if (!isCommentExist(author, comment)) {
            logger.warn("Không tìm thấy comment của tác giả '" + author + "' với nội dung: " + comment);
            return false;
        }

        String evaluteSelector = "//h3[text()='" + author + "']/ancestor::div[@class='reviewer-name d-flex']/.."
                + "//div[text()='Helpful?']/..//div[contains(@class,'" + (isHelpful ? "yes" : "no") + "')]";

        Locator evaluteBtn = page.locator(evaluteSelector);

        if (!evaluteBtn.isEnabled()) {
            logger.error("Nút đánh giá không khả dụng");
            return false;
        }

        clickByLocator(evaluteSelector);
        page.reload();
        page.waitForTimeout(5000);

        String activeSelector = evaluteSelector + "[contains(@class,'active')]";
        if (page.locator(activeSelector).isVisible()) {
            logger.info("Đã đánh giá thành công: {}", isHelpful ? "Yes" : "No");
            return true;
        } else {
            logger.warn("Đánh giá không được ghi nhận");
            return false;
        }
    }

    public boolean contactSeller(boolean status) {
        if (status) {
            this.chooseAccountStatus(status);
            page.navigate(UrlLocator.detailPageUrl);
            clickByLocator(DetailLocator.contactButton);
            page.waitForTimeout(5000);
            if (page.url().contains("seller")) {
                logger.info("đã điều hướng thành công");
                return true;
            } else {
                logger.error("điều hướng thất bại");
                return false;
            }
        } else {
            logger.error("Không thể liên hệ seller bằng tài khoản guest");
            return false;
        }
    }

    public boolean comment(String author, String think, double ratingStar, boolean status) {
        if (status) {
            this.chooseAccountStatus(status);
            typeByLocator(DetailLocator.commentTextBox, think);
            chooseStar(ratingStar);
            clickByLocator(DetailLocator.commentButton);
            page.waitForTimeout(4000);

            logger.info("Đã nhập comment: '" + think + "' với số sao: " + ratingStar);
            page.reload();
            if (isCommentExist(author, think)) {
                logger.info("đã comment thành công");
                return true;
            }
            return false;
        } else {
            this.chooseAccountStatus(false);
            logger.error("Không thể comment bằng tài khoản guest");
            return false;
        }
    }
    public boolean verifyAmountRating() {
        this.chooseAccountStatus(true);
        int count1 = extractNumber(getTextByLocator(DetailLocator.ratingAmountTop));
        logger.info(count1);
        int count2 = extractNumber(getTextByLocator(DetailLocator.ratingAmountBody));
        logger.info(count2);
        int count3 = extractNumber(getTextByLocator(DetailLocator.ratingAmountDown));
        logger.info(count3);
        int count4 = page.locator(DetailLocator.commentItem).count();
        logger.info(count4);
        if(count1 == count2 && count2 ==count3) {
            if(count1 == count4) {
                logger.info("số lượng rating hiển thị đồng bộ");
                return true;
            }else {
                logger.error("số lượng rating thực tế không giống rating hiển thị");
                return false;
            }
        }else {
            logger.error("số lượng rating hiển thị không đồng bộ");
            return false;
        }
    }
    public boolean verifyFaqLogic() {
        try {
            this.chooseAccountStatus(true);
            String pageTitle = getTextByLocator(DetailLocator.detailTitle);
            String aboutGig = getTextByLocator(DetailLocator.aboutDescription);
            List<String> faqTitles = getTextsByLocator(DetailLocator.faqTitle);
            List<String> faqContents = getTextsByLocator(DetailLocator.faqDescription);

            // 1. Kiểm tra trùng lặp tiêu đề FAQ
            Set<String> uniqueFaqTitles = new HashSet<>(faqTitles);
            if (faqTitles.size() != uniqueFaqTitles.size()) {
                logger.error("Lỗi: Có tiêu đề FAQ bị trùng lặp.");
                return false;
            }
            // 2. Kiểm tra trùng lặp nội dung FAQ
            Set<String> uniqueFaqContents = new HashSet<>(faqContents);
            if (faqContents.size() != uniqueFaqContents.size()) {
                logger.error("Lỗi: Có nội dung FAQ bị trùng lặp.");
                return false;
            }

            // 3. Ghép tất cả nội dung FAQ
            String faqContentString = String.join(" ", faqContents).toLowerCase();

            // 4. Lấy các từ khóa từ title và aboutGig
            Set<String> keywordsToCheck = new HashSet<>();
            if (pageTitle != null) {
                keywordsToCheck.addAll(Arrays.asList(pageTitle.toLowerCase().split("\\s+")));
            }
            if (aboutGig != null) {
                keywordsToCheck.addAll(Arrays.asList(aboutGig.toLowerCase().split("\\s+")));
            }

            // 5. Kiểm tra có từ khóa nào của title/aboutGig xuất hiện trong FAQ không
            boolean isRelated = false;
            for (String keyword : keywordsToCheck) {
                if (faqContentString.contains(keyword)) {
                    isRelated = true;
                    break;
                }
            }

            if (!isRelated) {
                logger.error("Lỗi: Nội dung FAQ không liên quan đến Title/About.");
                return false;
            }

            logger.info("Tất cả kiểm tra FAQ thành công.");
            return true;

        } catch (Exception e) {
            logger.error("Lỗi khi kiểm tra FAQ logic", e);
            return false;
        }
    }

    public boolean sortCommentByDropdown(String option) {
        try {
            if (!this.chooseAccountStatus(true)) return false;

            // Chuẩn hoá option -> value của <option>
            String opt = option == null ? "" : option.trim().toLowerCase();
            String value;
            if (opt.equals("recent") || opt.equals("most recent")) value = "recent";
            else if (opt.equals("relevant") || opt.equals("most relevant")) value = "relevant";
            else {
                logger.error("Option không hợp lệ. Chỉ được: recent | relevant");
                return false;
            }

            // Bảo đảm dropdown và list comment sẵn sàng
            waitForVisibleByLocator(DetailLocator.sortCommentDropdown);
            waitForVisibleByLocator(DetailLocator.commentItem);

            // Snapshot trước khi sort
            List<String> before = getTextsByLocator(DetailLocator.commentItem);

            // Thực hiện sort
            page.selectOption(DetailLocator.sortCommentDropdown, value);

            // Poll đến khi thứ tự thay đổi hoặc hết timeout
            long deadline = System.currentTimeMillis() + 7000;
            List<String> after = getTextsByLocator(DetailLocator.commentItem);
            while (System.currentTimeMillis() < deadline && after.equals(before)) {
                page.waitForTimeout(200);
                after = getTextsByLocator(DetailLocator.commentItem);
            }

            if (!after.equals(before)) {
                logger.info("Thứ tự comment đã thay đổi sau khi sort: {}", value);
                return true;
            } else {
                logger.warn("Thứ tự comment không thay đổi sau khi sort: {}", value);
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi sort comment bằng dropdown", e);
            return false;
        }
    }
    public boolean sortCommentByFilter(String keyword) {
        try {
            if (!this.chooseAccountStatus(true)) return false;

            // Bảo đảm control sẵn sàng
            waitForVisibleByLocator(DetailLocator.filterTextbox);
            waitForVisibleByLocator(DetailLocator.filterBtn);
            waitForVisibleByLocator(DetailLocator.commentItem);

            // Snapshot trước filter
            List<String> before = getTextsByLocator(DetailLocator.commentItem);

            // Clear + nhập keyword rồi filter
            page.locator(DetailLocator.filterTextbox).fill("");
            typeByLocator(DetailLocator.filterTextbox, keyword == null ? "" : keyword);
            clickByLocator(DetailLocator.filterBtn);

            // Poll đến khi danh sách thay đổi hoặc hết timeout
            long deadline = System.currentTimeMillis() + 7000;
            List<String> after = getTextsByLocator(DetailLocator.commentItem);
            while (System.currentTimeMillis() < deadline && after.equals(before)) {
                page.waitForTimeout(3000);
                after = getTextsByLocator(DetailLocator.commentItem);
            }

            if (after.isEmpty()) {
                // Chấp nhận trường hợp không có kết quả
                logger.info("Filter trả về 0 comment với keyword: '{}'", keyword);
                return !before.isEmpty(); // đổi từ có dữ liệu -> 0 coi như đã thay đổi
            }

            // Xác minh tất cả comment đều khớp keyword
            String kw = keyword == null ? "" : keyword.toLowerCase();
            boolean allMatch = after.stream()
                    .filter(s -> s != null)
                    .allMatch(s -> s.toLowerCase().contains(kw));

            if (!after.equals(before) && allMatch) {
                logger.info("Danh sách comment đã thay đổi và khớp keyword '{}'", keyword);
                return true;
            } else {
                logger.warn("Filter không làm thay đổi danh sách hoặc có comment không khớp keyword '{}'", keyword);
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi filter comment", e);
            return false;
        }
    }


    public boolean buyProduct(String level, boolean status) {
        if (!status) {
            logger.warn("không thể mua sản phẩm bằng tài khoản guest");
            return false;
        }
        try {
            this.chooseAccountStatus(status);

            if (level.equalsIgnoreCase(DetailData.basicLevel)) {
            } else if (level.equalsIgnoreCase(DetailData.standardLevel)) {
                clickByLocator(DetailLocator.standardLevel);
            } else if (level.equalsIgnoreCase(DetailData.premiumLevel)) {
                clickByLocator(DetailLocator.premiumLevel);
            } else {
                logger.error("Level không hợp lệ: {}", level);
                return false;
            }
            page.waitForTimeout(5000);
            clickByLocator(DetailLocator.continuteButton);
            page.waitForTimeout(3000);
            logger.info("Đã mua sản phẩm thành công với level: " + level);
            return true;

        }

        catch (Exception e) {
            logger.error("Lỗi khi mua sản phẩm", e);
            return false;
        }
    }

}
