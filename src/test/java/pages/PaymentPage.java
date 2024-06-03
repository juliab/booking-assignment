package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPage extends BasePage {

    @FindBy(css = "[data-testid='price-breakdown-total']")
    private WebElement totalPrice;

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Retrieves the total ticket price from the payment page.
     *
     * @return the total price as a double
     */
    public double retrieveTotalPrice() {
        return Double.parseDouble(totalPrice.getText().replaceAll("[^0-9.]", ""));
    }
}