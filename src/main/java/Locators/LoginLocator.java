package Locators;

public class LoginLocator {
    public static final String title = "//h2[normalize-space()='Sign In to Fiverr']";
    public static final String email = "//*[contains(@id,'email')]";
    public static final String password = "//*[contains(@id,'password')]";
    public static final String loginBtn = "//*[contains(@type,'submit')]";
    public static final String transferToRegisterBtn = "//*[contains(@class,'text_register')]";
    public static final String unseenPasswordBtn = "//div[@class='form-outline flex-fill mb-0']//button";
    public static final String emailEmptyNotification = "//span[text()='Email không được bỏ trống !']";
    public static final String passwordEmptyNotification = "//span[text()='Password không được bỏ trống !']";
    public static final String emailInvalidNotification = "//span[text()='Email không đúng định dạng !']";
    public static final String passwordInvalidNotification = "//span[text()='pass từ 6 - 32 ký tự !']";
    public static final String loginFailedNotification = "//div[@class='Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter']";
}
