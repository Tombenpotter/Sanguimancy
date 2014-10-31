package tombenpotter.sanguimancy.util;

import tombenpotter.sanguimancy.Sanguimancy;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LocationsHandler implements Serializable {

    private static HashMap<String, ArrayList<PortalLocation>> portals;
    private static LocationsHandler locationsHandler;
    private static final String fileName = "PortalLocations." + Sanguimancy.modid;

    private LocationsHandler() {
        portals = new HashMap<String, ArrayList<PortalLocation>>();
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
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println(Sanguimancy.modid + " ERROR: Portal Locations file not found.");
            c.printStackTrace();
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

    public static LocationsHandler getLocationsHandler() {
        if (locationsHandler == null || loadFile(fileName) == null) {
            locationsHandler = new LocationsHandler();
            return locationsHandler;
        } else {
            portals = loadFile(fileName);
            return locationsHandler;
        }
    }

    public void addLocation(String name, PortalLocation location) {
        ArrayList<PortalLocation> portalLocations = portals.get(name);
        if (portalLocations == null) {
            portals.put(name, new ArrayList<PortalLocation>());
        }
        if (!portals.get(name).isEmpty() && portalLocations.contains(location)) {
            System.out.println("Location " + name + " already exists.");
        } else {
            portals.get(name).add(location);
            System.out.println("Adding " + name);
        }
        updateFile(fileName, portals);
    }

    public void removeLocation(String name, PortalLocation location) {
        if (portals.get(name) != null && !portals.get(name).isEmpty()) {
            if (portals.get(name).contains(location)) {
                portals.get(name).remove(location);
                System.out.println("Removing " + name);
            } else {
                System.out.println("No location matching " + name);
            }
            updateFile(fileName, portals);
        }
    }

    public ArrayList<PortalLocation> getLinkedLocations(String name) {
        return portals.get(name);
    }
}
