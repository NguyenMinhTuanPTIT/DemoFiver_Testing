package Locators;

public class ProfileLocator {
    public static final String editProfile="(//*[@class='edit'])[1]";
    public static final String productItems= "//div[@class='row']";
    public static final String removeProductBtn="//button:has-text('DEL')";
    public static final String btnViewdetail="(//*[contains(text(),'View detail')])";
    public static final String titlePodup="//h2[contains(normalize-space(),'Update User')]";
    public static final String emailTitle=".info_profile_label p";
    public static final String editPhoneNumber="//*[contains(@name,'phone')]";
    public static final String editName="//*[contains(@name,'name')]";
    public static final String editEmail="//*[contains(@type,'email')]";
    public static final String editBirthday="//*[contains(@name,'birthday')]";
    public static final String optionMales="//input[@value='male']";
    public static final String optionFemales="//input[@value='female']";
    public static final String editCertificate="//input[@id='certification']";
    public static final String editSkill="//input[@id='skill']";
    public static final String btnCancel="//button[contains(normalize-space(),'Cancel')]";
    public static final String btnSave="//button[contains(normalize-space(),'Save')]";
    public static final String createNewGigBtn="button:has_text('Create a new Gig')";

    public static final String transferToFacebook="a:has-text('Facebook')";
    public static final String transferToGoogle="a:has-text('Google')";
    public static final String transferToGithub="a:has-text('Github')";
    public static final String transferToTwitter="a:has-text('Twitter')";
    public static final String transferToDirbble="a:has-text('Dirbble')";
    public static final String transferToStackOverFlow="a:has-text('Stack Overflow')";




}
