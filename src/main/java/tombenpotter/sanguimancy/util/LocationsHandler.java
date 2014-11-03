package tombenpotter.sanguimancy.util;

import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.Sanguimancy;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LocationsHandler implements Serializable {

    private static HashMap<String, ArrayList<PortalLocation>> portals;
    private static LocationsHandler locationsHandler;
    private static final String fileName = String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.modid + "PortalLocations.dat";

    private LocationsHandler() {
        portals = new HashMap<String, ArrayList<PortalLocation>>();
    }

    public static LocationsHandler getLocationsHandler() {
        if (locationsHandler == null || loadFile(fileName) == null) {
            locationsHandler = new LocationsHandler();
            return locationsHandler;
        } else {
            portals = loadFile(fileName);
            return locationsHandler;
        }
    }

    private static HashMap<String, ArrayList<PortalLocation>> loadFile(String filename) {
        HashMap<String, ArrayList<PortalLocation>> map = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap<String, ArrayList<PortalLocation>>) in.readObject();
            in.close();
            fileIn.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: " + filename + " was not found in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
            return null;
        }
    }

    private static void updateFile(String filename, HashMap<String, ArrayList<PortalLocation>> object) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addLocation(String name, PortalLocation location) {
        ArrayList<PortalLocation> portalLocations = portals.get(name);
        if (portalLocations == null) {
            portals.put(name, new ArrayList<PortalLocation>());
            updateFile(fileName, portals);
        }
        if (!portals.get(name).isEmpty() && portals.get(name).size() > 2) {
            Sanguimancy.logger.info("Location " + name + " already exists.");
            updateFile(fileName, portals);
            return false;
        } else {
            portals.get(name).add(location);
            Sanguimancy.logger.info("Adding " + name);
            updateFile(fileName, portals);
            return true;
        }
    }

    public boolean removeLocation(String name, PortalLocation location) {
        if (portals.get(name) != null && !portals.get(name).isEmpty()) {
            if (portals.get(name).contains(location)) {
                portals.get(name).remove(location);
                Sanguimancy.logger.info("Removing " + name);
                updateFile(fileName, portals);
                return true;
            } else {
                Sanguimancy.logger.info("No location matching " + name);
                updateFile(fileName, portals);
                return false;
            }
        }
        return false;
    }

    public ArrayList<PortalLocation> getLinkedLocations(String name) {
        return portals.get(name);
    }
}
