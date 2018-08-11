package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Performance {
    private String _startTime;
    private String _id;
    private static int _performanceIdCounter=0;
    private Ticket[][] _tickets;

    public Performance(String startTime, int seatingDimension, int premiumPrice, int cheapPrice, String theatreID){

        _startTime=startTime;
        _id="performance"+String.valueOf(++_performanceIdCounter);

        _tickets=new Ticket[seatingDimension][seatingDimension];

        // Create ticket objects for each seating location with correct pricing according to their row
        for (int row=0;row<seatingDimension;row++){
            for (int col=0;col<seatingDimension;col++){
                _tickets[row][col]=new Ticket(theatreID,row+1,col+1,_id);

                if (row<seatingDimension/2) {
                    // If seat is in front half of theatre
                    _tickets[row][col].ticketPrice(premiumPrice);

                } else {
                    // If seat is in back half of theatre
                    _tickets[row][col].ticketPrice(cheapPrice);
                }
            }
        }
    }

    public String getID() {
        return _id;
    }

    public List<String> getTicketIDs(){
        List<String> performanceIDs=new ArrayList<String>();

        // Add ID for each ticket to list
        for (Ticket[] ticketRow:_tickets) {
            for (Ticket ticket:ticketRow) {
                performanceIDs.add(ticket.getID());
            }
        }

        return performanceIDs;
    }

    public String issueTicket(int row, int col){
        try {
            // Check if seat has already been taken
            if (_tickets[row-1][col-1].isAvailable()) {
                return _tickets[row - 1][col - 1].issueTicket();
            }
            return "ERROR: Seat has already been taken";
        }
        // If theatre does not have input row and column
        catch (ArrayIndexOutOfBoundsException e){
            return "ERROR: Seat does not exist";
        }
    }

    public List<String> seatsAvailable(){
        List<String> seatsAvailable=new ArrayList<String>();

        // Add each available ticket ID to list
        for (Ticket[] ticketRow:_tickets) {
            for (Ticket ticket:ticketRow) {
                if (ticket.isAvailable()) {
                    seatsAvailable.add(ticket.getSeatLocation());
                }
            }
        }

        return seatsAvailable;
    }

    public String salesMade(){
        // Start string with performance ID and its starting time
        String performanceReport= _id + "\t" + _startTime + "\t";

        String salesAndReceipts;

        // Counts for tickets sold & price of total tickets sold
        int ticketsSold=0;
        int salesReceipts=0;

        // Check every ticket
        for (Ticket[] ticketRow:_tickets){
            for (Ticket ticket:ticketRow){

                // If seat has been taken
                if (!ticket.isAvailable()){
                    // Increase tickets sold by 1
                    ticketsSold++;

                    // Increase total price by price of ticket sold
                    salesReceipts+=ticket.getPrice();
                }
            }
        }

        // Convert tickets sold and price of tickets sold into string with correct format
        salesAndReceipts=Integer.toString(ticketsSold) + "\t$" + Integer.toString(salesReceipts);

        return performanceReport + salesAndReceipts;
    }
}
