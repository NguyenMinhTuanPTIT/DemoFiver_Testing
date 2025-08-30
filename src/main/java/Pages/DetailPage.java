package Pages;

import Base.BasePage;
import com.microsoft.playwright.Page;
import Data.*;
import Locators.*;

public class DetailPage extends BasePage {
    public DetailPage(Page page, String baseUrl) {
        super(page, baseUrl);
    }

    public void chooseAccountStatus(boolean status) {
        if (status) {
            navigate(UrlLocator.LOGIN_URL);
            LoginPage loginPage = new LoginPage(page, baseUrl);
            loginPage.login(LoginData.Email, LoginData.password);
            navigate(UrlLocator.detailPageUrl);
        } else {
            navigate(UrlLocator.detailPageUrl);
        }
    }
    public void buyProductByUser(String level) {
        this.chooseAccountStatus(true);
        if (level.equals("Basic")) {
        } else if (level.equals("Standard")) {
            click(DetailLocator.standardLevel);
        } else if (level.equals("Premium")) {
            click(DetailLocator.premiumPrice);
        }
        click(DetailLocator.continuteButton);
    }
    public void compareProduct() {
        this.chooseAccountStatus(true);
        navigate(UrlLocator.detailPageUrl);
        click(DetailLocator.compareButton);
    }

    public void comment(String think,float ratingStar) {
        this.chooseAccountStatus(true);
        fill(DetailLocator.commentTextBox,think);
        click(DetailLocator.commentButton);


    }

}
