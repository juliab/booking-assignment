package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends BasePage {

    @FindBy(css = "[data-testid='sr-list'] [data-testid='card-title']")
    private WebElement attractionCard;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects an attraction by name. Switches to a new window after clicking the
     * attraction card.
     * 
     * @param name attraction name
     * @return Attractions Page instance for chaining
     */
    public TicketConfigPage selectAttraction(String name) {
        performActionAndSwitchToNewWindow(() -> attractionCard(name).click());
        return new TicketConfigPage(driver, name);
    }

    private WebElement attractionCard(String name) {
        wait(2, NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(attractionCard));
        return attractionCard.findElement(By.xpath("./*[contains(text(), '" + name + "')]"));
    }
}
