package Locators;

public class HomeLocator {

    //header
    public static final String username="//nav//li//p";
    public static final String logo ="//a[@class='logo active']";
    public static final String searchHeaderBar="//input[@placeholder='Find Services']";
    public static final String searchHeaderBtn="//div[@class='header_search']//button";
    public static final String fiverBusinessBtn= "//nav[@class='header_navbar']//li[text()='Fiverr Business']";
    public static final String exploreBtn="//nav[@class='header_navbar']//li[text()='Explore']";
    public static final String changeLanguageBtn="//nav[@class='header_navbar']//li[text()='English']";
    public static final String changeCurrencyBtn="//nav[@class='header_navbar']//li[text()='US$ USD']";
    public static final String becomeSellerBtn="//nav[@class='header_navbar']//li[text()='Become a Seller']";
    public static final String loginBtn="//nav[@class='header_navbar']//a[text()='Sign in']";
    public static final String joinBtn="//nav[@class='header_navbar']//a[text()='Join']";
    public static final String profileBtn ="//a[@href='/profile']";
    public static final String navigateHeaderItems="//div[@class='categoriesmenu_li']//p";

    //body
    public static final String searchBodyBar="//form[@role='search']//input";
    public static final String searchBodyBtn="//form[@role='search']//button";
    public static final String navigateBodyItems="//div[@class='d-flex popular']//div";
    public static final String imgList="//h2[text()='Popular professional services']/..//div[@class=\"slick-track\"]";
    public static final String previousImgBtn ="//div[@class='slider-package']//div[@class='slick-arrow slick-prev']";
    public static final String nextImgBtn="//div[@class=\"slider-package\"]//div[@class=\"slick-arrow slick-next\"]";
    public static final String videoOnScreen="//video";
    public static final String videoWorldBtn="//div[@class=\"selling-proposition container\"]//button";

    public static final String videoListItems="//section[@class=\"testimonial\"]//div[@class=\"slick-track\"]//div[@aria-hidden=\"true\"]";
    public static final String previousListBtn ="//section[@class=\"testimonial\"]//div[@class='slick-arrow slick-prev']";
    public static final String nextListBtn="//section[@class=\"testimonial\"]//div[@class='slick-arrow slick-next']";

    public static final String categoryList="//h2[text()='Explore the marketplace']/..//a";


}
