package tombenpotter.sanguimancy.api.tile;

import java.util.ArrayList;

public interface IBoundTile {

    public ArrayList<String> getOwnersList();

    public void setOwnersList(ArrayList<String> ownersList);

    public void addOwnerToList(String ownerName);

    public void removeOwnerFromList(String ownerName);
}
