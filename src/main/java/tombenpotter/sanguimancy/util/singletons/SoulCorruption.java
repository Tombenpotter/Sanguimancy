package tombenpotter.sanguimancy.util.singletons;

import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.Sanguimancy;

import java.io.*;
import java.util.HashMap;

public class SoulCorruption {
    private static final String fileName = String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.texturePath + "/SoulCorruption.dat";
    private static SoulCorruption soulCorruption;
    private HashMap<String, Integer> corruptionMap;

    private SoulCorruption() {
    }

    public static SoulCorruption getSoulCorruption() {
        if (soulCorruption == null) {
            soulCorruption = new SoulCorruption();
            soulCorruption.loadFile();
        }
        if (soulCorruption.corruptionMap == null) {
            soulCorruption.corruptionMap = new HashMap<String, Integer>();
            soulCorruption.updateFile();
        }
        return soulCorruption;
    }

    private void loadFile() {
        HashMap<String, Integer> map = null;
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
            map = (HashMap<String, Integer>) in.readObject();
            in.close();
            fileIn.close();
            corruptionMap = map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Sanguimancy.logger.error(String.valueOf(file) + " was not found in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
        }
    }

    private void updateFile() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(corruptionMap);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean setCorruption(String name, int level) {
        if (level > 0) corruptionMap.put(name, level);
        else corruptionMap.put(name, 0);
        updateFile();
        return true;
    }

    private boolean hasPlayer(String name) {
        return corruptionMap.containsKey(name);
    }

    public int getCorruption(String name) {
        if (!hasPlayer(name)) corruptionMap.put(name, 0);
        return corruptionMap.get(name);
    }
}
