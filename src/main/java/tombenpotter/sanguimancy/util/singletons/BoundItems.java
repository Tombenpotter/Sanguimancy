package tombenpotter.sanguimancy.util.singletons;

import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.BoundItemState;

import java.io.*;
import java.util.HashMap;

public class BoundItems implements Serializable {

    private static HashMap<String, BoundItemState> items;
    private static BoundItems boundItems;
    private static final String fileName = String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.texturePath + "/BoundItems.dat";

    private BoundItems() {
        items = new HashMap<String, BoundItemState>();
    }

    public static BoundItems getBoundItems() {
        if (boundItems == null || loadFile(fileName) == null) {
            boundItems = new BoundItems();
            return boundItems;
        } else {
            items = loadFile(fileName);
            return boundItems;
        }
    }

    private static HashMap<String, BoundItemState> loadFile(String name) {
        HashMap<String, BoundItemState> map = null;
        File file = new File(name);
        try {
            if (!file.exists()) {
                if (file.getParentFile().mkdir()) {
                    if (file.createNewFile()) {
                        Sanguimancy.logger.info("Creating " + name + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                    }
                } else if (file.createNewFile()) {
                    Sanguimancy.logger.info("Creating " + name + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                } else {
                    throw new IOException("Failed to create directory " + file.getParent());
                }
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap<String, BoundItemState>) in.readObject();
            in.close();
            fileIn.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            Sanguimancy.logger.error(String.valueOf(file) + " was not found in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
            return null;
        }
    }

    private static void updateFile(String file, HashMap<String, BoundItemState> object) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addItem(String name, BoundItemState location) {
        if (!items.isEmpty() && items.get(name) != null) {
            Sanguimancy.logger.info("State " + name + " already exists.");
            updateFile(fileName, items);
            return false;
        } else {
            items.put(name, location);
            Sanguimancy.logger.info("Adding " + name);
            updateFile(fileName, items);
            return true;
        }
    }

    public boolean removeItem(String name) {
        if (items != null && !items.isEmpty() && items.get(name) != null) {
            if (items.containsKey(name)) {
                items.remove(name);
                Sanguimancy.logger.info("Removing " + name);
                updateFile(fileName, items);
                return true;
            } else {
                Sanguimancy.logger.info("No state matching " + name);
                updateFile(fileName, items);
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

