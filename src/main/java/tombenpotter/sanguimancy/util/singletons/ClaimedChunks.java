package tombenpotter.sanguimancy.util.singletons;

import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.ChunkIntPairSerializable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClaimedChunks {
    private HashMap<String, ArrayList<ChunkIntPairSerializable>> chunks;
    private static ClaimedChunks claimedChunks;
    private static final String fileName = String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.texturePath + "/ClaimedChunks.dat";

    private ClaimedChunks() {
    }

    public static ClaimedChunks getClaimedChunks() {
        if (claimedChunks == null) {
            claimedChunks = new ClaimedChunks();
            claimedChunks.loadFile();
        }
        if (claimedChunks.chunks == null) {
            claimedChunks.chunks = new HashMap<String, ArrayList<ChunkIntPairSerializable>>();
            claimedChunks.updateFile();
        }
        return claimedChunks;
    }

    private void loadFile() {
        HashMap<String, ArrayList<ChunkIntPairSerializable>> map = null;
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
            map = (HashMap<String, ArrayList<ChunkIntPairSerializable>>) in.readObject();
            in.close();
            fileIn.close();
            chunks = map;
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
            oos.writeObject(chunks);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addLocation(String name, ChunkIntPairSerializable location) {
        ArrayList<ChunkIntPairSerializable> chunkLocation = chunks.get(name);
        if (chunkLocation == null) {
            chunks.put(name, new ArrayList<ChunkIntPairSerializable>());
            updateFile();
        }
        for (String string : chunks.keySet()) {
            if (chunks.get(string).contains(location)) {
                Sanguimancy.logger.info("Chunk already claimed by: " + string);
                updateFile();
                return false;
            }
        }
        chunks.get(name).add(location);
        Sanguimancy.logger.info(name + " claiming chunk");
        updateFile();
        return true;
    }

    public boolean removeLocation(String name, ChunkIntPairSerializable location) {
        if (chunks.get(name) != null && !chunks.get(name).isEmpty()) {
            if (chunks.get(name).contains(location)) {
                chunks.get(name).remove(location);
                Sanguimancy.logger.info("Removing " + name);
                updateFile();
                return true;
            } else {
                Sanguimancy.logger.info("No claimed chunk matching " + name);
                updateFile();
                return false;
            }
        }
        return false;
    }

    public ArrayList<ChunkIntPairSerializable> getLinkedChunks(String name) {
        return chunks.get(name);
    }
}