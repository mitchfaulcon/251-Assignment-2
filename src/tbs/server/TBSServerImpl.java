package tbs.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TBSServerImpl implements TBSServer {

    private List<Theatre> _theatres = new ArrayList<Theatre>();
    private List<Artist> _artists = new ArrayList<Artist>();

    @Override
    public String initialise(String path) {

        try{
            Scanner x=new Scanner(new File(path));

            // Loop through until end of file
            while (x.hasNext()) {
                //ignore "THEATRE"
                x.next();

                String id = x.next();
                int seatingDim = Integer.parseInt(x.next());
                int floorSpace = Integer.parseInt(x.next());

                // Add new theatre object to list of theatres
                _theatres.add(new Theatre(id,seatingDim,floorSpace));

            }
            return "";
        }
        catch(FileNotFoundException e){
            return "ERROR: File not found";
        }

        catch(NullPointerException e){
            return "ERROR: File location cannot be null";
        }

        catch(NumberFormatException e){
            return "ERROR: Incorrect file format";
        }

    }

    @Override
    public List<String> getTheatreIDs() {
        List<String> IDs=new ArrayList<String>();
        // Fill list with each theatre id
        for (Theatre theatre:_theatres){
            IDs.add(theatre.getID());
        }

        for (int i=0;i<IDs.size();i++){
            for (int j=i+1;j<IDs.size();j++){
                if (IDs.get(i).compareTo(IDs.get(j))>0){
                    // Swap IDs into order
                    String temp=IDs.get(i);
                    IDs.set(i,IDs.get(j));
                    IDs.set(j,temp);
                }
            }
        }

        return IDs;
    }

    @Override
    public List<String> getArtistIDs() {
        List<String> IDs=new ArrayList<String>();
        // Fill list with each artist id
        for (Artist artist:_artists){
            IDs.add(artist.getID());
        }

        for (int i=0;i<IDs.size();i++){
            for (int j=i+1;j<IDs.size();j++){
                if (IDs.get(i).compareTo(IDs.get(j))>0){
                    // Swap IDs into order
                    String temp=IDs.get(i);
                    IDs.set(i,IDs.get(j));
                    IDs.set(j,temp);
                }
            }
        }

        return IDs;
    }

    @Override
    public List<String> getArtistNames() {
        List<String> names=new ArrayList<String>();
        // Fill list with each artist name
        for (Artist artist:_artists){
            names.add(artist.getName());
        }

        for (int i=0;i<names.size();i++){
            for (int j=i+1;j<names.size();j++){
                if (names.get(i).compareTo(names.get(j))>0){
                    // Swap names into order
                    String temp=names.get(i);
                    names.set(i,names.get(j));
                    names.set(j,temp);
                }
            }
        }

        return names;
    }

    @Override
    public List<String> getActIDsForArtist(String artistID) {

        List<String> IDList=new ArrayList<String>();

        // Check if input is empty or null
        try {
            if (artistID.equals("")) {
                IDList.add("ERROR: Artist ID cannot be empty");
                return IDList;
            }
        }
        catch(NullPointerException e){
            IDList.add("ERROR: Artist ID cannot be null");
            return IDList;
        }

        // Find artist with input id
        boolean idFound=false;
        int artistIndex=0;
        for (Artist artist:_artists){
            if (artist.getID().equals(artistID)){
                idFound=true;
                break;
            }
            artistIndex++;
        }
        // If no artist was found
        if (!idFound){
            IDList.add("ERROR: No artist with entered id exists");
            return IDList;
        }

        // Add act IDs for artist to list
        IDList=_artists.get(artistIndex).getActIds();

        return IDList;
    }

    @Override
    public List<String> getPeformanceIDsForAct(String actID) {
        List<String> IDList=new ArrayList<String>();

        // Check if input is empty or null
        try {
            if (actID.equals("")) {
                IDList.add("ERROR: Act ID cannot be empty");
                return IDList;
            }
        }
        catch(NullPointerException e){
            IDList.add("ERROR: Act ID cannot be null");
            return IDList;
        }

        // Find artist and act with input id
        boolean actFound=false;
        int artistIndex=0;
        int actIndex=0;
        List<String> actIDs=new ArrayList<String>();
        searchLoop:
        for (Artist artist:_artists){
            actIDs=artist.getActIds();
            actIndex=0;
            for (String id:actIDs){
                if (id.equals(actID)){
                    actFound=true;
                    break searchLoop;
                }
                actIndex++;
            }
            artistIndex++;
        }
        // If no act with entered id was found
        if (!actFound){
            IDList.add("ERROR: No act with entered id exists");
            return IDList;
        }

        // Add performance IDs for act to list
        IDList=_artists.get(artistIndex).getPerformanceIDsForAct(actIndex);

        return IDList;
    }

    @Override
    public List<String> getTicketIDsForPerformance(String performanceID) {
        List<String> IDList=new ArrayList<String>();

        // Check if input is empty or null
        try {
            if (performanceID.equals("")) {
                IDList.add("ERROR: Performance ID cannot be empty");
                return IDList;
            }
        }
        catch(NullPointerException e){
            IDList.add("ERROR: Performance ID cannot be null");
            return IDList;
        }

        // Find performance with input id
        boolean performanceFound=false;
        int artistIndex=0;
        int actIndex=0;
        int performanceIndex=0;
        List<String> actIDs=new ArrayList<String>();
        List<String> performanceIDs=new ArrayList<String>();
        searchLoop:
        for (Artist artist:_artists){
            actIDs=artist.getActIds();
            actIndex=0;
            for (String act:actIDs){
                performanceIDs=artist.getPerformanceIDsForAct(actIndex);
                performanceIndex=0;
                for (String performance:performanceIDs){
                    if (performance.equals(performanceID)){
                        performanceFound=true;
                        break searchLoop;
                    }
                    performanceIndex++;
                }
                actIndex++;
            }
            artistIndex++;
        }

        // If no performance with input id was found
        if (!performanceFound){
            IDList.add("ERROR: No performance with entered id exists");
            return IDList;
        }

        // Add ticket IDs for performance to list
        IDList=_artists.get(artistIndex).getTicketIDsForPerformanceOfAct(actIndex,performanceIndex);

        return IDList;
    }

    @Override
    public String addArtist(String name) {

        try {
            // Check for empty name or null input
            if (name.equals("")) {
                return "ERROR: Name cannot be empty";
            }
        }
        catch(NullPointerException E) {
            return "ERROR: Name cannot be null";
        }

        // Check if name is already in server
        for (Artist artist : _artists) {
            if (artist.getName().equalsIgnoreCase(name)) {
                return "ERROR: artist already exists";
            }
        }

        _artists.add(new Artist(name));
        return _artists.get(_artists.size() - 1).getID();
    }

    @Override
    public String addAct(String title, String artistID, int minutesDuration) {
        try {
            // Check for empty name or null input
            if (title.equals("")) {
                return "ERROR: Act title is empty";
            }
        }
        catch(NullPointerException e){
            return "ERROR: Title cannot be null";
        }

        // Check if act duration is less than or equal to zero
        if (minutesDuration<=0){
            return "ERROR: Act duration must be longer than zero minutes";
        }

        // Find artist with input id
        boolean idFound=false;
        int artistIndex=0;
        for (Artist artist:_artists){
            if (artist.getID().equals(artistID)){
                idFound=true;
                break;
            }
            artistIndex++;
        }
        // If no artist was found
        if (!idFound){
            return "ERROR: No artist with entered id exists";
        }

        // Add act to artist and return act id
        return _artists.get(artistIndex).addAct(title,minutesDuration);
    }

    @Override
    public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {

        // Check that date format is correct
        try {
            if (!(startTimeStr.length() == 16 && startTimeStr.charAt(4) == '-' && startTimeStr.charAt(7) == '-' && startTimeStr.charAt(10) == 'T' && startTimeStr.charAt(13) == ':')) {
                return "ERROR: Incorrect date format - Must be ISO 8601";
            }
        }
        catch(NullPointerException e){
            return "ERROR: Incorrect date format - Must be ISO 8601";
        }

        // Check that price format is correct
        try {
            if (premiumPriceStr.charAt(0) != '$' || cheapSeatsStr.charAt(0) != '$') {
                return "ERROR: Incorrect price format - Must be $d where d is the number of dollars";
            }
        }
        catch(NullPointerException | StringIndexOutOfBoundsException e){
            return "ERROR: Incorrect price format - Must be $d where d is the number of dollars";
        }

        // Find theatre with input id
        boolean theatreFound=false;
        int theatreIndex=0;
        for (Theatre theatre:_theatres){
            if (theatre.getID().equals(theatreID)){
                theatreFound=true;
                break;
            }
            theatreIndex++;
        }
        // If no theatre with entered id exists
        if (!theatreFound){
            return "ERROR: No theatre with entered id exists";
        }

        // Find artist and act with input id
        boolean actFound=false;
        int artistIndex=0;
        int actIndex=0;
        List<String> actIDs=new ArrayList<String>();
        searchLoop:
        for (Artist artist:_artists){
            actIDs=artist.getActIds();
            actIndex=0;
            for (String id:actIDs){
                if (id.equals(actID)){
                    actFound=true;
                    break searchLoop;
                }
                actIndex++;
            }
            artistIndex++;
        }
        // If no act with entered id was found
        if (!actFound){
            return "ERROR: No act with entered id exists";
        }

        int seatingDimension=_theatres.get(theatreIndex).getSeatingDim();
        int premiumPrice=Integer.parseInt(premiumPriceStr.substring(1));
        int cheapPrice=Integer.parseInt(cheapSeatsStr.substring(1));

        // Schedule performance and return performance id
        return _artists.get(artistIndex).schedulePerformance(actIndex,startTimeStr,seatingDimension,premiumPrice,cheapPrice,theatreID);
    }

    @Override
    public String issueTicket(String performanceID, int rowNumber, int seatNumber) {

        // Find performance with input id
        boolean performanceFound=false;
        int artistIndex=0;
        int actIndex=0;
        int performanceIndex=0;
        List<String> actIDs=new ArrayList<String>();
        List<String> performanceIDs=new ArrayList<String>();
        searchLoop:
        for (Artist artist:_artists){
            actIDs=artist.getActIds();
            actIndex=0;
            for (String act:actIDs){
                performanceIDs=artist.getPerformanceIDsForAct(actIndex);
                performanceIndex=0;
                for (String performance:performanceIDs){
                    if (performance.equals(performanceID)){
                        performanceFound=true;
                        break searchLoop;
                    }
                    performanceIndex++;
                }
                actIndex++;
            }
            artistIndex++;
        }

        // If no performance with input id exists
        if (!performanceFound){
            return "ERROR: No performance with input id exists";
        }

        // Issue ticket for performance and return ticket id
        return _artists.get(artistIndex).issueTicketForPerformanceOfAct(actIndex,performanceIndex,rowNumber,seatNumber);
    }

    @Override
    public List<String> seatsAvailable(String performanceID) {
        List<String> seatsAvailable=new ArrayList<String>();

        // Find performance with input id
        boolean performanceFound=false;
        int artistIndex=0;
        int actIndex=0;
        int performanceIndex=0;
        List<String> actIDs=new ArrayList<String>();
        List<String> performanceIDs=new ArrayList<String>();
        searchLoop:
        for (Artist artist:_artists){
            actIDs=artist.getActIds();
            actIndex=0;
            for (String act:actIDs){
                performanceIDs=artist.getPerformanceIDsForAct(actIndex);
                performanceIndex=0;
                for (String performance:performanceIDs){
                    if (performance.equals(performanceID)){
                        performanceFound=true;
                        break searchLoop;
                    }
                    performanceIndex++;
                }
                actIndex++;
            }
            artistIndex++;
        }

        // If no performance with input id exists
        if (!performanceFound){
            seatsAvailable.add("ERROR: No performance with input id exists");
            return seatsAvailable;
        }

        // Add available seats for performance to list
        seatsAvailable=_artists.get(artistIndex).seatsAvailableForPerformanceOfAct(actIndex,performanceIndex);

        return seatsAvailable;
    }

    @Override
    public List<String> salesReport(String actID) {

        List<String> salesReport=new ArrayList<String>();

        // Find act with input id
        boolean actFound=false;
        int artistIndex=0;
        int actIndex=0;
        List<String> actIDs=new ArrayList<String>();
        searchLoop:
        for (Artist artist:_artists){
            actIDs=artist.getActIds();
            actIndex=0;
            for (String id:actIDs){
                if (id.equals(actID)){
                    actFound=true;
                    break searchLoop;
                }
                actIndex++;
            }
            artistIndex++;
        }
        // If no act with entered id was found
        if (!actFound){
            salesReport.add("ERROR: No act with entered id exists");
            return salesReport;
        }

        // Add details of each performance of act to list
        salesReport=_artists.get(artistIndex).salesReportForAct(actIndex);

        return salesReport;
    }

    @Override
    public List<String> dump() {
        return null;
    }
}