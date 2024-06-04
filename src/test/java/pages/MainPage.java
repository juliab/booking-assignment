package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import util.ConfigUtil;

public class MainPage extends BasePage {

    @FindBy(id = "attractions")
    private WebElement attractionsLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the main page. URL of the page is retrieved from the configuration.
     * 
     * @return MainPage instance for chaining
     */
    public MainPage open() {
        driver.navigate().to(ConfigUtil.getBaseUrl());
        return this;
    }

    /**
     * Clicks the Attractions link in the header of the main page.
     * 
     * @return AttractionsPage instance
     */
    public AttractionsPage clickAttractionsLink() {
        attractionsLink.click();
        return new AttractionsPage(driver);
    }
}
