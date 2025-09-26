package Base;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.List;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setupBrowser() {
        logger.info("=== Khởi tạo Playwright và Browser cho test mới ===");
        playwright = Playwright.create();

        // Chromium: dùng List.of(...) cho setArgs
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized"))
        );

        // viewport = null để lấy full màn hình hiện tại
        context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null)
        );

        page = context.newPage();
        logger.info("=== Browser sẵn sàng cho test case ===");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test {} thất bại", testName, result.getThrowable());
            try {
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                Allure.addAttachment("Screenshot - " + testName, "image/png",
                        new ByteArrayInputStream(screenshot), ".png");
                Allure.addAttachment("Log - " + testName, result.getThrowable().toString());
            } catch (Exception e) {
                logger.error("Không thể chụp screenshot cho test {}", testName, e);
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test {} thành công", testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test {} bị skip", testName);
        }

        logger.info("=== Đóng Browser cho test case {} ===", testName);
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
