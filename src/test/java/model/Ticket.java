package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a ticket for an attraction.
 */
public class Ticket {
    private String attractionName;
    private LocalDate date;
    private String time;
    private Set<PriceRange> priceRanges = new HashSet<>();

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void addPriceRange(String category, double price, int numberOfTickets) {
        this.priceRanges.add(new PriceRange(category, price, numberOfTickets));
    }

    public double getTotalPrice() {
        return priceRanges.stream()
                .mapToDouble(PriceRange::getTotalPrice)
                .sum();
    }
}
