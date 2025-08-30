package Locators;

public class RegisterLocator {
    public static final String lbRegisterTitle="//h2[normalize-space()='REGISTER']";
    public static final String ipUser="//*[contains(@id,'name')]";
    public static final String ipPw="(//*[contains(@id,'password')])[1]";
    public static final String ipRpw="(//*[contains(@id,'password')])[2]";
    public static final String ipPhone="//*[contains(@id,'phone')]";
    public static final String ipBrithday="//*[contains(@id,'birthday')]";
    public static final String opionMale ="//*[contains(text(),'Male')]";
    public static final String opionFemale ="//*[contains(text(),'Female')]";
    public static final String cbox_agreeitem="//*[contains(@id,'agree-term')]";
    public static final String aLogin="//*[contains(text(),'I am already member')]";
    public static final String btnSubmit="//button[normalize-space()='Submit']";

}
