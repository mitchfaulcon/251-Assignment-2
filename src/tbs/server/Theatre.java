package tbs.server;

public class Theatre {
    private String _id;
    private int _seatingDim;
    private int _floorSpace;

    public Theatre(String id, int seatingDim, int floorSpace){
        _id=id;
        _seatingDim=seatingDim;
        _floorSpace=floorSpace;
    }

    public String getID() {
        return _id;
    }

    public int getSeatingDim(){
        return _seatingDim;
    }

}
