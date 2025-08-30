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
    public static final String standardTitle ="(//span[text()='Basic'])[2]";
    public static final String standardDescription ="//div[@id='standard']//p";
    public static final String standardPrice ="(//span[text()='Standard']/following::span[contains(text(),'US$')])[3]";
    public static final String premiumLevel="(//button[text()='Premium'])[2]";
    public static final String premiumTitle ="(//span[text()='Premium'])[2]";
    public static final String premiumDescription ="//div[@id='premium']//p";
    public static final String premiumPrice ="(//span[text()='Premium']/following::span[contains(text(),'US$')])[1]";
    //button submit
    public static final String continuteButton = "(//button[@class=\"submit\"])[2]";
    public static final String compareButton="(//a[@href=\"#compare\"])[2]";
    //about
    public static final String aboutTitle ="//div[@class='job-description mt-5']//h2";
    public static final String aboutDescription ="//div[@class='job-description mt-5']//div";
    //about seller
    public static final String starRatedBody ="//div[@class=\"profile-label\"]//div[@class=\"star-score\"]";
    public static final String ratingAmountBody ="//div[@class=\"profile-label\"]//div[@class=\"rating\"]";
    public static final String contactButton ="//button[text()='Contact Me']";
    //Faq
    public static final String faqTitle="//div[@class=\"FAQ mt-5\"]//h2";
    public static final String info1Title ="(//div[@class=\"FAQ mt-5\"]//li//h3)[1]";
    public static final String info2Title ="(//div[@class=\"FAQ mt-5\"]//li//h3)[2]";
    public static final String info3Title ="(//div[@class=\"FAQ mt-5\"]//li//h3)[3]";
    public static final String info4Title ="(//div[@class=\"FAQ mt-5\"]//li//h3)[4]";
    public static final String info1Description ="(//div[@class=\"FAQ mt-5\"]//li//p)[1]";
    public static final String info2Description ="(//div[@class=\"FAQ mt-5\"]//li//p)[2]";
    public static final String info3Description ="(//div[@class=\"FAQ mt-5\"]//li//p)[3]";
    public static final String info4Description ="(//div[@class=\"FAQ mt-5\"]//li//p)[4]";
    //rating
    public static final String ratingAmountDown ="//h2[@class='mb-0 me-2']";
    public static final String amount1star="(//div[@class=\"review-rating mt-3 row\"]//td[@class=\"star-num\"])[5]";
    public static final String amount2star="(//div[@class=\"review-rating mt-3 row\"]//td[@class=\"star-num\"])[4]";
    public static final String amount3star="(//div[@class=\"review-rating mt-3 row\"]//td[@class=\"star-num\"])[3]";
    public static final String amount4star="(//div[@class=\"review-rating mt-3 row\"]//td[@class=\"star-num\"])[2]";
    public static final String amount5star="(//div[@class=\"review-rating mt-3 row\"]//td[@class=\"star-num\"])[1]";

    //comment
    public static final String commentTextBox ="//div[@class=\"add-comment py-4\"]/.//textarea";
    public static final String commentButton ="button:has_text('Comment')";

}
