package tombenpotter.sanguimancy.util.singletons;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.Sanguimancy;

import java.io.*;
import java.util.HashMap;

public class SNBoundItems implements Serializable {

    private static SNBoundItems snBoundItems;
    private static HashMap<String, byte[]> boundItems;
    private static final String fileName = String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.texturePath + "/SNBoundItems.dat";

    private SNBoundItems() {
        boundItems = new HashMap<String, byte[]>();
    }

    public static SNBoundItems getSNBountItems() {
        if (snBoundItems == null || loadFile(fileName) == null) {
            snBoundItems = new SNBoundItems();
            return snBoundItems;
        } else {
            boundItems = loadFile(fileName);
            return snBoundItems;
        }
    }

    private static HashMap<String, byte[]> loadFile(String name) {
        HashMap<String, byte[]> map = null;
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
            map = (HashMap<String, byte[]>) in.readObject();
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

    private static void updateFile(String file, HashMap<String, byte[]> object) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addItem(String name, byte[] b) {
        if (!boundItems.isEmpty() && boundItems.containsValue(b)) {
            Sanguimancy.logger.info("Item " + name + " already exists.");
            updateFile(fileName, boundItems);
            return false;
        } else {
            boundItems.put(name, b);
            Sanguimancy.logger.info("Adding " + name);
            updateFile(fileName, boundItems);
            return true;
        }
    }

    public boolean addItem(String name, NBTTagCompound nbt) {
        try {
            if (nbt != null) {
                byte[] compressed = CompressedStreamTools.compress(nbt);
                return addItem(name, compressed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeItem(String name) {
        if (!boundItems.isEmpty()) {
            if (boundItems.get(name) != null) {
                boundItems.remove(name);
                Sanguimancy.logger.info("Removing " + name);
                updateFile(fileName, boundItems);
                return true;
            } else {
                Sanguimancy.logger.info("No item matching " + name);
                updateFile(fileName, boundItems);
                return false;
            }
        }
        return false;
    }

    public byte[] getItemB(String name) {
        return boundItems.get(name);
    }

    public boolean hasKey(String name) {
        return boundItems.containsKey(name);
    }

    public NBTTagCompound getItemT(String name) {
        byte[] compressed = getItemB(name);
        try {
            if (compressed != null) {
                return CompressedStreamTools.func_152457_a(compressed, new NBTSizeTracker(2097152L));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
