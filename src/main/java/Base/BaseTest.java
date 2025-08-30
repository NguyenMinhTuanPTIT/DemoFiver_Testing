package Base;
import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();

        // Ưu tiên khởi tạo trình duyệt: Chrome → Firefox → WebKit
        try {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } catch (Exception e1) {
            try {
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
            } catch (Exception e2) {
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
            }
        }

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)
                .setLocale("en-US")
        );

        page = context.newPage();
        page.setDefaultTimeout(30000);
    }

    @AfterClass
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}

