package Locators;

public class LoginLocator {
    public static final String lbsigninTitle="//h2[normalize-space()='Sign In to Fiverr']";
    public static final String email="//*[contains(@id,'email')]";
    public static final String password="//*[contains(@id,'password')]";
    public static final String btnLogin="//*[contains(@type,'submit')]";
    public static final String aResgister="//*[contains(@class,'text_register')]";
    public static final String unseenPasswordBtn ="//div[@class=\"form-outline flex-fill mb-0\"]//button";
    public static final String emailError_Locator = "//span[@class='text-danger' and normalize-space()='Email không được bỏ trống !']";
    public static final String emailFormatError_Locator = "//span[@class='text-danger' and normalize-space()='Email không đúng định dạng !']";

}
