package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Act {
    private String _title;
    private int _minutesDuration;
    private String _id;
    private static int _actIdCounter=0;
    private List<Performance> _performances=new ArrayList<Performance>();

    public Act(String title, int duration){
        _title=title;
        _minutesDuration=duration;
        _id="act"+String.valueOf(++_actIdCounter);
    }

    public String getID() {
        return _id;
    }

    public String getTitle() {
        return _title;
    }

    public String schedulePerformance(String startTimeStr,int seatingDimension, int premiumPrice, int cheapPrice, String theatreID){

        // Add performance to act
        _performances.add(new Performance(startTimeStr,seatingDimension,premiumPrice,cheapPrice,theatreID));

        // Return new performance id
        return _performances.get(_performances.size()-1).getID();
    }

    public List<String> getPerformanceIds(){
        List<String> performanceIDs=new ArrayList<String>();

        // Add the ID of each performance into the list
        for(Performance performance:_performances){
            performanceIDs.add(performance.getID());
        }

        return performanceIDs;
    }

    public List<String> getTicketIDsForPerformance(int performanceIndex){

        // Return all ticket IDs for input performance
        return _performances.get(performanceIndex).getTicketIDs();
    }

    public String issueTicketForPerformance(int performanceIndex, int row, int col){

        // Issue ticket for input performance and return issued ticket ID
        return _performances.get(performanceIndex).issueTicket(row,col);
    }

    public List<String> seatsAvailableForPerformance(int performanceIndex){

        // Return a list of all seats available for input performance
        return _performances.get(performanceIndex).seatsAvailable();
    }

    public List<String> salesReport(){
        List<String> salesReport=new ArrayList<String>();

        // Add sales details for each performance to list
        for (Performance performance:_performances){
            salesReport.add(performance.salesMade());
        }

        return salesReport;
    }
}
