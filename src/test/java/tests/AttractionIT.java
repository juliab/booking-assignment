package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import model.Ticket;
import pages.MainPage;
import pages.PaymentPage;
import pages.TicketConfigPage;
import util.DayPeriod;

public class AttractionIT extends BaseTestClass {

    @Test
    public void totalAmountShouldBeCalculatedCorrectly() throws InterruptedException {
        TicketConfigPage ticketConfigPage = new MainPage(driver).open()
                .clickAttractionsLink()
                .searchForLocation("Vancouver")
                .selectAttraction("Capilano Suspension Bridge Park Admission")
                .selectDate(10)
                .selectTime(9, 0, DayPeriod.AM)
                .selectNumberOfTickets("Adult (age 18–64)", 2)
                .selectNumberOfTickets("Child (age 13–17)", 1)
                .selectNumberOfTickets("Child (age 6–12)", 1);

        Ticket ticket = ticketConfigPage.getTicket();
        PaymentPage paymentPage = ticketConfigPage.clickNext();
        Assert.assertEquals(paymentPage.retrieveTotalPrice(), ticket.getTotalPrice());
    }
}
