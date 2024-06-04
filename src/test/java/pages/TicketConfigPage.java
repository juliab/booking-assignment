package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import model.Ticket;
import util.DayPeriod;

/**
 * This class represents a ticket configuration page.
 * It contains methods to select the date, time, number of tickets, and to
 * proceed to the payment page.
 * This class maintains a Ticket instance that is updated with the selected
 * options.
 */
public class TicketConfigPage extends BasePage {

    private Ticket ticket = new Ticket();

    @FindBy(css = "[data-testid='datepicker']")
    private WebElement datePicker;

    @FindBy(css = "[data-testid='timeslot-selector']")
    private WebElement timeSlotSelector;

    @FindBy(css = "[data-testid='inline-ticket-config']")
    private WebElement ticketConfigSection;

    @FindBy(css = "[data-testid='ticket-selector-stepper']")
    private List<WebElement> ticketSelectorStepper;

    @FindBy(css = "[data-testid='select-ticket']")
    private WebElement nextButton;

    private By previousDatesButtonSelector = By.cssSelector("[aria-label='Previous']");
    private By nextDatesButtonSelector = By.cssSelector("[aria-label='Next']");

    public TicketConfigPage(WebDriver driver, String attractionName) {
        super(driver);
        ticket.setAttractionName(attractionName);
    }

    /**
     * Retrieves the ticket instance that contains the selected options.
     * 
     * @return Ticket instance
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Calculates the date to be selected that is a certain number of days from
     * today.
     * Selects the date on the date picker.
     * 
     * The method is not implemented for more than 11 days from today.
     * 
     * @param numberOfDaysFromToday number of days from today
     * @return TicketConfigPage instance for chaining
     */
    public TicketConfigPage selectDate(int numberOfDaysFromToday) {
        if (numberOfDaysFromToday > 11) {
            throw new IllegalArgumentException("Not implemented for more than 11 days from today");
        }

        LocalDate date = LocalDate.now().plusDays(numberOfDaysFromToday);
        clickDateElement(date);
        ticket.setDate(date);
        return this;
    }

    private void clickDateElement(LocalDate date) {
        String day = date.format(DateTimeFormatter.ofPattern("d"));
        String month = date.format(DateTimeFormatter.ofPattern("MMM"));
        By dayLocator = By.xpath(".//button[contains(., '" + day + "') and contains(., '" + month + "')]");

        do {
            try {
                datePicker.findElement(dayLocator).click();
                break;
            } catch (NoSuchElementException e) {
                throw new RuntimeException(
                        "Cannot select the date " + day + " " + month + ". The date is not available.");
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                clickNextDatesButton();
            }
        } while (true);
    }

    private void clickNextDatesButton() {
        WebElement previousDatesButton = datePicker.findElement(previousDatesButtonSelector);
        WebElement nextDatesButton = datePicker.findElement(nextDatesButtonSelector);

        String previousDatesClass = previousDatesButton.getAttribute("class");
        String nextDatesClass = nextDatesButton.getAttribute("class");

        nextDatesButton.click();

        // wait for the previous and next buttons to change their classes
        // (this is an indicator that the dates have been updated)
        try {
            wait(2).until(d -> !previousDatesButton.getAttribute("class").equals(previousDatesClass)
                    || !nextDatesButton.getAttribute("class").equals(nextDatesClass));
        } catch (TimeoutException e) {
            // sometimes conditions are not met, so we just try again
            // TODO investigate why this happens
        }
    }

    /**
     * Selects the time slot on the ticket configuration page.
     * 
     * @param hour   hour of the time slot
     * @param minute minute of the time slot
     * @param period period of the time slot (AM or PM)
     * @return TicketConfigPage instance for chaining
     */
    public TicketConfigPage selectTime(int hour, int minute, DayPeriod period) {
        checkIfTimeSlotSelectorIsPresent();
        String time = convertTime(hour, minute, period);
        By timeOptionSelector = By
                .xpath(".//input[@data-testid='timeslot-option']/parent::*[contains(., '" + time + "')]");
        timeSlotSelector.findElement(timeOptionSelector).click();
        ticket.setTime(time);
        return this;
    }

    private void checkIfTimeSlotSelectorIsPresent() {
        try {
            timeSlotSelector.isDisplayed();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Time slot selector is not displayed");
        }
    }

    private String convertTime(int hour, int minute, DayPeriod period) {
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);
        String formattedPeriod = period.toString();
        return formattedHour + ":" + formattedMinute + " " + formattedPeriod;
    }

    /**
     * Selects the number of tickets for the specified age category.
     * 
     * @param ageCategory     age category of the ticket
     * @param numberOfTickets number of tickets to select
     * @return TicketConfigPage instance for chaining
     */
    public TicketConfigPage selectNumberOfTickets(String ageCategory, int numberOfTickets) {
        WebElement row = ticketSelectorStepper.stream()
                .filter(e -> e.findElement(By.tagName("label")).getText().contains(ageCategory))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("No ticket selector found for the age category: " + ageCategory));

        selectNumberOfTickets(row, numberOfTickets);
        ticket.addPriceRange(ageCategory, readPrice(row), numberOfTickets);

        return this;
    }

    private void selectNumberOfTickets(WebElement row, int numberOfTickets) {
        WebElement addButton = row.findElement(By.cssSelector("button:last-of-type"));
        WebElement numberOfTicketsLabel = row.findElement(By.xpath(".//*[button]"));

        do {
            addButton.click();
        } while (!numberOfTicketsLabel.getText().equals(String.valueOf(numberOfTickets)));
    }

    private double readPrice(WebElement row) {
        WebElement priceElement = row.findElement(By.tagName("label")).findElement(By.xpath("./following-sibling::*"));
        return Double.parseDouble(priceElement.getText().replaceAll("[^0-9.]", ""));
    }

    /**
     * Proceeds to the payment page.
     * 
     * @return PaymentPage instance
     */
    public PaymentPage clickNext() {
        nextButton.click();
        return new PaymentPage(driver);
    }
}
