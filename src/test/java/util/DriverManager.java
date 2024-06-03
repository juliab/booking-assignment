package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This class is used to manage the webdriver instance and provides methods to
 * get
 * and close the webdriver instance.
 */
public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    private static WebDriver getDriver() {
        return driver.get();
    }

    private static void setDriver(WebDriver d) {
        driver.set(d);
    }

    /**
     * This method returns the webdriver instance. If the webdriver instance is not
     * created, it creates a new instance and returns it.
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriverInstance() {
        if (getDriver() == null) {
            setDriver(new ChromeDriver());
        }
        return getDriver();
    }

    /**
     * This method closes the webdriver instance.
     */
    public static void terminate() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }
}
