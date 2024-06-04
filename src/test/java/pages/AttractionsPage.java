package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AttractionsPage extends BasePage {

    @FindBy(css = "input[data-testid='search-input-field']")
    private WebElement searchInputField;

    @FindBy(css = "button[data-testid='search-button']")
    private WebElement searchButton;

    @FindBy(css = "a[data-testid='search-bar-result']")
    private List<WebElement> searchResult;

    public AttractionsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Searches for a location by name:
     * - types the name into the search input field,
     * - waits for the search results to appear,
     * - selects the first search result that contains the name,
     * - and clicks the search button.
     * 
     * @param name location name
     * @return SearchResultsPage instance
     */
    public SearchResultsPage searchForLocation(String name) {
        searchInputField.sendKeys(name);
        firstLocationSearchResult(name).click();
        searchButton.click();
        return new SearchResultsPage(driver);
    }

    private WebElement firstLocationSearchResult(String name) {
        String errorMessage = "No search result found by the name: " + name;

        try {
            wait(1).until(d -> !searchResult.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException(errorMessage);
        }

        return searchResult.stream()
                .filter(e -> e.getText().contains(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(errorMessage));
    }
}
