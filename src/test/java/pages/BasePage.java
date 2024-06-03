package pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 * This class represents a base page. Classes representing pages of the
 * application should extend this class.
 */
public abstract class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Performs the specified action, usually clicking a link or a button, that
     * opens a new window.
     * Then waits for a new window to open and switches to it.
     * 
     * @param action action to perform
     */
    protected void performActionAndSwitchToNewWindow(Runnable action) {
        Set<String> originalHandles = driver.getWindowHandles();
        action.run();
        wait(2).until(ExpectedConditions.numberOfWindowsToBe(originalHandles.size() + 1));
        driver.getWindowHandles().stream()
                .filter(h -> !originalHandles.contains(h))
                .findFirst()
                .ifPresentOrElse(h -> driver.switchTo().window(h), () -> {
                    throw new RuntimeException("New window not found");
                });
    }

    /**
     * Returns a Wait instance that waits for the specified number of seconds
     * and polls every 1/6 of the specified number of seconds.
     * 
     * @param seconds number of seconds to wait
     * @return Wait instance
     */
    protected Wait<WebDriver> wait(int seconds) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(seconds * 100 / 6));
    }

    /**
     * Returns a Wait instance that waits for the specified number of seconds
     * and polls every 1/6 of the specified number of seconds, ignoring the
     * specified exception type.
     * 
     * @param seconds       number of seconds to wait
     * @param exceptionType exception type to ignore
     * @return Wait instance
     */
    protected Wait<WebDriver> wait(int seconds, Class<? extends Throwable> exceptionType) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(seconds * 100 / 6))
                .ignoring(exceptionType);
    }
}
