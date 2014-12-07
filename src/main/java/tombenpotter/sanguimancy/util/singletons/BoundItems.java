package tombenpotter.sanguimancy.util.singletons;

import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.BoundItemState;

import java.io.*;
import java.util.HashMap;

public class BoundItems {
    private HashMap<String, BoundItemState> items;
    private static BoundItems boundItems;
    private static final String fileName = String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.texturePath + "/BoundItems.dat";

    private BoundItems() {

    }

    public static BoundItems getBoundItems() {
        /*if (boundItems == null && loadFile(fileName) == null) {
            boundItems = new BoundItems();
            updateFile(fileName, items);
            return boundItems;
        } else {
            items = loadFile(fileName);
            return boundItems;
        }*/

        if( boundItems == null )
        {
            boundItems = new BoundItems();
            boundItems.loadFile();
        }

        if( boundItems.items == null )
        {
            boundItems.items = new HashMap<String, BoundItemState>();
            boundItems.updateFile();
        }

        return boundItems;
    }

    private /*HashMap<String, BoundItemState>*/void loadFile() {
        HashMap<String, BoundItemState> map = null;
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                if (file.getParentFile().mkdir()) {
                    if (file.createNewFile()) {
                        Sanguimancy.logger.info("Creating " + fileName + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                    }
                } else if (file.createNewFile()) {
                    Sanguimancy.logger.info("Creating " + fileName + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                } else {
                    throw new IOException("Failed to create directory " + file.getParent());
                }
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap<String, BoundItemState>) in.readObject();
            in.close();
            fileIn.close();
            items = map;
            //return map;
        } catch (IOException e) {
            e.printStackTrace();
            //return null;
        } catch (ClassNotFoundException e) {
            Sanguimancy.logger.error(String.valueOf(file) + " was not found in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
        }
    }

    private void updateFile() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addItem(String name, BoundItemState location) {
        if (!items.isEmpty() && items.get(name) != null) {
            Sanguimancy.logger.info("Location " + name + " already exists.");
            updateFile();
            return false;
        } else {
            items.put(name, location);
            Sanguimancy.logger.info("Adding " + name);
            updateFile();
            return true;
        }
    }

    public boolean removeItem(String name) {
        if (items.get(name) != null && !items.isEmpty()) {
            if (items.containsKey(name)) {
                items.remove(name);
                Sanguimancy.logger.info("Removing " + name);
                updateFile();
                return true;
            } else {
                Sanguimancy.logger.info("No location matching " + name);
                updateFile();
                return false;
            }
        }
        return false;
    }

    public boolean hasKey(String name) {
        return items.containsKey(name);
    }

    public BoundItemState getLinkedLocation(String name) {
        return items.get(name);
    }
}