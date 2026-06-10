import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class PointFormTest {

    @Test
    public void testSimpleInput() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/");
                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");
                page.click("button:has-text('Очистить')");

                page.locator(".input-row").nth(0).locator("input").fill("0");
                page.locator(".input-row").nth(1).locator("input").fill("0");
                page.locator(".input-row").nth(2).locator("input").fill("1");
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Попадание");
            }
        }
    }

    @Test
    public void testInputButton() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/");
                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");
                page.click("button:has-text('Очистить')");
                page.locator(".input-row").nth(0).locator("input").fill("0");
                page.locator(".input-row").nth(1).locator("input").fill("0");
                page.locator(".input-row").nth(2).locator("input").fill("1");

                page.locator(".input-row").nth(0).locator("button:has-text('+')").click();
                page.locator(".input-row").nth(1).locator("button:has-text('-')").click();
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Промах");
            }
        }
    }
}
