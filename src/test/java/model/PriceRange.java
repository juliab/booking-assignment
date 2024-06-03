package model;

/**
 * Represents a price range for a specific age category.
 * It contains the age category, the price for that age category, and the number
 * of tickets for that age category.
 */
public class PriceRange {
    private String ageCategory;
    private double price;
    private int numberOfTickets;

    public PriceRange(String ageCategory, double price, int numberOfTickets) {
        this.ageCategory = ageCategory;
        this.price = price;
        this.numberOfTickets = numberOfTickets;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public double getTotalPrice() {
        return price * numberOfTickets;
    }
}
