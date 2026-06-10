import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class AuthTest {

    @Test
    public void testSuccessfulRegistration() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/");

                page.click(".link-btn");
                page.fill("input[name='login']", "NewUser2");
                page.fill("input[name='password']", "Pass2");
                page.click("button[type='submit']");

                assertThat(page.locator(".status-msg")).hasText("Успешная регистрация");
            }
        }
    }

    @Test
    public void testSuccessfulAuth() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/");

                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");

                assertThat(page).hasURL("http://localhost:8080/WebLab4/main");
            }
        }
    }

    @Test
    public void testEmptyAuth() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/");

                page.click("button[type='submit']");

                assertThat(page).hasURL("http://localhost:8080/WebLab4/");
            }
        }
    }

    @Test
    public void testInvalidRegistration() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/");

                page.click(".link-btn");
                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");

                assertThat(page.locator(".status-msg")).hasText("Логин занят");
            }
        }
    }

    @Test
    public void testMainProtection() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500))) {

                Page page = browser.newPage();
                page.navigate("http://localhost:8080/WebLab4/main");

                assertThat(page.locator("body")).containsText("Not Found");
            }
        }
    }
}