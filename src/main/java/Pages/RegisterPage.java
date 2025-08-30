package Pages;

import Base.BasePage;
import com.microsoft.playwright.Page;
import Locators.*;
public class RegisterPage extends BasePage {
    public RegisterPage(Page page, String baseUrl) {
        super(page, baseUrl);
    }
    public void register(String name,String email,String password,String confirmPass,String phone,String birthday,boolean isMale,boolean isConfirm) {
        navigate(UrlLocator.registerPageUrl);
        fill(RegisterLocator.ipUser,name);
        fill(RegisterLocator.ipPw,password);
        fill(RegisterLocator.ipRpw,confirmPass);
        fill(RegisterLocator.ipPhone,phone);
        fill(RegisterLocator.ipBrithday,birthday);
        if(isMale) {
            click(RegisterLocator.opionMale);
        }else {
            click(RegisterLocator.opionFemale);
        }
        if(isConfirm) {
            click(RegisterLocator.cbox_agreeitem);
        }
        click(RegisterLocator.btnSubmit);
    }
    public void transferToLoginPage() {
        navigate(UrlLocator.registerPageUrl);
        click(RegisterLocator.aLogin);
    }
}
