package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private String _name;
    private String _id;
    private static int _artistIdCounter=0;
    private List<Act> _acts=new ArrayList<Act>();

    public Artist(String name){
        _name=name.toLowerCase();
        _id="artist"+String.valueOf(++_artistIdCounter);
    }

    public String getID() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String addAct(String title, int duration){
        // Check if artist already contains act with same name
        for(Act act:_acts){
            if(act.getTitle().equalsIgnoreCase(title)){
                return "ERROR: Artist already contains act with entered name";
            }
        }
        // Add act to artist
        _acts.add(new Act(title,duration));
        // Return new act id
        return _acts.get(_acts.size()-1).getID();
    }

    public List<String> getActIds() {

        List<String> actIDs=new ArrayList<String>();

        // Add the ID for each act into the list
        for(Act act:_acts){
            actIDs.add(act.getID());
        }

        return actIDs;
    }

    public String schedulePerformance(int actIndex, String startTimeStr,int seatingDimension, int premiumPrice, int cheapPrice, String theatreID){

        // Schedule performance for input act and return ID for performance
        return _acts.get(actIndex).schedulePerformance(startTimeStr,seatingDimension,premiumPrice,cheapPrice,theatreID);
    }

    public List<String> getPerformanceIDsForAct(int actIndex) {

        // Return all performance IDs for input act
        return _acts.get(actIndex).getPerformanceIds();
    }

    public List<String> getTicketIDsForPerformanceOfAct(int actIndex, int performanceIndex){

        // Return all ticket IDs for input act and performance ID
        return _acts.get(actIndex).getTicketIDsForPerformance(performanceIndex);
    }

    public String issueTicketForPerformanceOfAct(int actIndex, int performanceIndex, int row, int col){

        // Issue a ticket for a performance of input act
        return _acts.get(actIndex).issueTicketForPerformance(performanceIndex,row,col);
    }

    public List<String> seatsAvailableForPerformanceOfAct(int actIndex, int performanceIndex){

        // Return all available seats for performance of input act
        return _acts.get(actIndex).seatsAvailableForPerformance(performanceIndex);
    }

    public List<String> salesReportForAct(int actIndex){

        // Return list containing sales details of each performance of input act
        return _acts.get(actIndex).salesReport();
    }
}
