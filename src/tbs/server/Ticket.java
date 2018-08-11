package tbs.server;

public class Ticket {
    private int _row;
    private int _col;
    private boolean _occupied=false;
    private int _price;
    private String _id;

    public Ticket(String theatreID, int row, int col, String performanceID){
        _row=row;
        _col=col;

        _id=theatreID + "r" + row + "c" + col + performanceID;
    }

    public void ticketPrice(int price){
        _price=price;
    }

    public String getID() {
        return _id;
    }

    public String issueTicket(){
        _occupied=true;
        return _id;
    }

    public boolean isAvailable(){
        return !_occupied;
    }

    public String getSeatLocation(){
        return _row + "\t" + _col;
    }

    public int getPrice(){
        return _price;
    }
}
