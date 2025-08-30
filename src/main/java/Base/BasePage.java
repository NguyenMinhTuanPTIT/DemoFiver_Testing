package Base;

import com.microsoft.playwright.Page;

public class BasePage {
    protected Page page;
    protected String baseUrl;

    public BasePage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    // ==== Common navigation ====
    public void navigate(String url) {
        if (baseUrl != null && !baseUrl.isEmpty()) {
            page.navigate(baseUrl + url);
        } else {
            page.navigate(url);
        }
    }

    // ==== Common actions ====
    public void click(String locator) {
        page.locator(locator).click();
    }

    public void fill(String locator, String text) {
        page.locator(locator).fill(text);
    }

//    public void selectItemInListByText(String locator, String text) {
//        page.locator(locator).filter(new Page.Locator.FilterOptions().setHasText(text)).first().click();
//    }
}
