package Locators;

public class DetailLocator {
    public static final String detailTitle = "//h1";
    //seller
    public static final String levelSeller = "//div[@class='seller-level']";
    public static final String starRatedTop = "//div[@class='seller-overview d-flex flex-wrap gap-3 align-items-center']//div[@class='star-score']";
    public static final String ratingAmountTop ="//div[@class='seller-overview d-flex flex-wrap gap-3 align-items-center']//div[@class='rating']";
    public static final String orderInQueue = "//div[@class='seller-ordered']";
    //level product
    public static final String basicLevel ="(//button[text()='Basic'])[2]";
    public static final String basicTitle ="(//span[text()='Basic'])[2]";
    public static final String basicDescription ="//div[@id='basic']//p";
    public static final String basicPrice ="(//span[text()='Basic']/following::span[contains(text(),'US$')])[4]";
    public static final String standardLevel="(//button[text()='Standard'])[2]";
    public static final String standardTitle ="(//span[text()='Standard'])[2]";
    public static final String standardDescription ="//div[@id='standard']//p";
    public static final String standardPrice ="(//span[text()='Standard']/following::span[contains(text(),'US$')])[3]";
    public static final String premiumLevel="(//button[text()='Premium'])[2]";
    public static final String premiumTitle ="(//span[text()='Premium'])[2]";
    public static final String premiumDescription ="//div[@id='premium']//p";
    public static final String premiumPrice ="(//span[text()='Premium']/following::span[contains(text(),'US$')])[1]";
    //button submit
    public static final String continuteButton = "(//button[@class='submit'])[2]";
    public static final String compareButton="(//a[@href=\"#compare\"])[2]";
    //about
    public static final String aboutTitle ="//div[@class='job-description mt-5']//h2";
    public static final String aboutDescription ="//div[@class='job-description mt-5']//div";
    //about seller
    public static final String starRatedBody ="//div[@class=\"profile-label\"]//div[@class=\"star-score\"]";
    public static final String ratingAmountBody ="//div[@class=\"profile-label\"]//div[@class=\"rating\"]";
    public static final String contactButton ="//button[text()='Contact Me']";
    //Faq
    public static final String faqTitle="//div[@class='FAQ mt-5']//li//h3";
    public static final String faqDescription ="//div[@class='FAQ mt-5']//li//p";
    //rating
    public static final String ratingAmountDown ="//h2[@class='mb-0 me-2']";
    public static final String sortCommentDropdown ="//span[text()='Sort By']/..//select";
    public static final String filterTextbox ="//h3[text()='Filters']/..//input";
    public static final String filterBtn ="//h3[text()='Filters']/..//button";

    //comment
    public static final String commentAuthor="//li[@class='row py-4']//h3";
    public static final String commentItem="(//ul[@class='review-comment-list']//p)";
    public static final String commentTextBox ="//div[@class='add-comment py-4']/.//textarea";
    public static final String commentButton ="button:has-text('Comment')";
    public static final String halfRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='1']//div[contains(@class,'first')]";
    public static final String oneRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='1']//div[contains(@class,'second')]";
    public static final String onehalfRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='2']//div[contains(@class,'first')]";
    public static final String twoRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='2']//div[contains(@class,'second')]";
    public static final String twohalfRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='3']//div[contains(@class,'first')]";
    public static final String threeRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='3']//div[contains(@class,'second')]";
    public static final String threehalfRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='4']//div[contains(@class,'first')]";
    public static final String fourRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='4']//div[contains(@class,'second')]";
    public static final String fourhalfRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='5']//div[contains(@class,'first')]";
    public static final String fiveRatingStar="//div[@class='d-flex align-items-center gap-1']//div[@aria-posinset='5']//div[contains(@class,'second')]";


}
