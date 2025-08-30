package Tests;
import Base.*;
import Pages.LoginPage;
import Pages.ProfilePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.DetailPage;
import Locators.*;
import Data.*;
public class DetailTest extends BaseTest{
    private DetailPage detailPage ;
    private ProfilePage profilePage;

    @BeforeMethod
    public void init() {
        detailPage = new DetailPage(page);
        profilePage = new ProfilePage(page);
    }

    @Test
    public void testBuyProduct() {
        page.navigate("https://demo5.cybersoft.edu.vn/jobDetail/26");
        detailPage.waitForVisibleByLocator(DetailLocator.detailTitle);
        detailPage.buyProductByUser(DetailData.premiumLevel,DetailData.status);

    }

}
