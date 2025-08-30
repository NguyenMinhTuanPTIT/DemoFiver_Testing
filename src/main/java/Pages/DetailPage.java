package Pages;

import Base.BasePage;
import com.microsoft.playwright.Page;
import Data.*;
import Locators.*;

public class DetailPage extends BasePage {
    public DetailPage(Page page) {
        super(page);
    }

    public void chooseAccountStatus(boolean status) {
        if (status) {
            page.navigate(UrlLocator.LOGIN_URL);
            LoginPage loginPage = new LoginPage(page);
            loginPage.login(LoginData.email, LoginData.password);
            page.navigate(UrlLocator.detailPageUrl);
        } else {
            page.navigate(UrlLocator.detailPageUrl);
        }
    }
    public void chooseStar(float expectStar) {
        // chỉ nhận giá trị từ 0.5 đến 5.0 và chia hết cho 0.5
        if (expectStar < 0.5 || expectStar > 5 || Math.abs(expectStar % 0.5) > 1e-9) {
            throw new IllegalArgumentException("Số sao phải từ 0.5 đến 5.0 và chia hết cho 0.5");
        }

        int key = (int)(expectStar * 10);

        switch (key) {
            case 5:   clickByLocator(DetailLocator.halfRatingStar); break;
            case 10:  clickByLocator(DetailLocator.oneRatingStar); break;
            case 15:  clickByLocator(DetailLocator.onehalfRatingStar); break;
            case 20:  clickByLocator(DetailLocator.twoRatingStar); break;
            case 25:  clickByLocator(DetailLocator.twohalfRatingStar); break;
            case 30:  clickByLocator(DetailLocator.threeRatingStar); break;
            case 35:  clickByLocator(DetailLocator.threehalfRatingStar); break;
            case 40:  clickByLocator(DetailLocator.fourRatingStar); break;
            case 45:  clickByLocator(DetailLocator.fourhalfRatingStar); break;
            case 50:  clickByLocator(DetailLocator.fiveRatingStar); break;
            default: throw new IllegalStateException("Không tìm thấy locator cho số sao " + expectStar);
        }
    }
    public boolean isCommentExist(String comment) {
        this.chooseAccountStatus(true);
        page.waitForTimeout(4000);
        int count = page.locator(DetailLocator.commentItem).count();
        if (count == 0) {
            logger.warn("Không có comment trong danh sách");
            return false;
        }
        String commentName = null;
        for (int i = 0; i < count; i++) {
            String commentSelector = DetailLocator.commentItem + "[" + (i + 1) + "]";
            commentName = getTextByLocator(commentSelector);

            logger.info("Kiểm tra comment thứ " + (i + 1) + ": '" + commentName + "'");

            if (commentName != null && commentName.equalsIgnoreCase(comment)) {
                logger.info("Đã tìm thấy comment khớp: " + commentName);
                return true;
            }
        }

        logger.warn("Không tìm thấy comment nào là: '" + commentName + "'");
        return false;


    }
    public boolean compareProduct() {
        this.chooseAccountStatus(true);
        page.navigate(UrlLocator.detailPageUrl);
        clickByLocator(DetailLocator.compareButton);
        page.waitForTimeout(5000);
        if(page.url().contains("compare")) {
            logger.info("đã điều hướng thành công");
            return true;
        }else{
            logger.error("điều hướng thất bại");
            return false;
        }
    }
    public void evaluateComment(String author,boolean isHelpful,boolean status) {
        if(status){}
        else {
            this.chooseAccountStatus(status);
            logger.error("Không thể đánh giá comment bằng tài khoản guest");
        }

    }
    public boolean contactSeller(boolean status) {
        if(status) {
            this.chooseAccountStatus(status);
            page.navigate(UrlLocator.detailPageUrl);
            clickByLocator(DetailLocator.contactButton);
            page.waitForTimeout(5000);
            if(page.url().contains("seller")) {
                logger.info("đã điều hướng thành công");
                return true;
            }else{
                logger.error("điều hướng thất bại");
                return false;
            }
        }else {
            logger.error("Không thể liên hệ seller bằng tài khoản guest");
            return false;
        }

    }

    public boolean comment(String think, float ratingStar, boolean status) {
        if (status) {
            this.chooseAccountStatus(status);
            typeByLocator(DetailLocator.commentTextBox,think);
            chooseStar(ratingStar);
            clickByLocator(DetailLocator.commentButton);

            logger.info("Đã nhập comment: '" + think + "' với số sao: " + ratingStar);
            page.reload();
            if(isCommentExist(think)) {
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

    public void buyProductByUser(String level,boolean status) {

        if(status) {
            this.chooseAccountStatus(status);
            if (level.equalsIgnoreCase("Basic")) {
            } else if (level.equalsIgnoreCase("Standard")) {
                clickByLocator(DetailLocator.standardLevel);
            } else if (level.equalsIgnoreCase("Premium")) {
                clickByLocator(DetailLocator.premiumPrice);
            }
            clickByLocator(DetailLocator.continuteButton);
        }
        else {
            logger.warn("không thể mua sản phẩm bằng tài khoản guest");
        }
    }


}
