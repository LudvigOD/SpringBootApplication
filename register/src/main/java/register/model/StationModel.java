package register.model;

public abstract class StationModel {
    private static int nbrStations = 0;
    protected int id;

    public StationModel() {
        id = nbrStations++;
    }

    public int id() {
        return id;
    }
}
