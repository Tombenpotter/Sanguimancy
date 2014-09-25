package tombenpotter.sanguimancy.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TreeFinder {
    /**
     * public Triplet findStartLog(World world, int x, int y, int z) {
     * for (int i = x - 1; i <= x + 1; i++) {
     * for (int j = y - 3; j <= y; j++) {
     * for (int k = z - 1; k <= z + 1; k++) {
     * if (world.getBlock(i, j, k).isWood(world, i, j, k)) {
     * Triplet startLogCoords = new Triplet(i, j, k);
     * return startLogCoords;
     * }
     * }
     * }
     * }
     * return null;
     * }
     */
    public Block[] getAdjacentBlocks(World world, int x, int y, int z) {
        Block[] blocks = new Block[6];
        int i = 0;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            blocks[i] = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            i = i + 1;
        }
        return blocks;
    }

    public boolean[] isAdjacentBlockLog(World world, int x, int y, int z) {
        boolean[] isLog = new boolean[6];
        int i = 0;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (world.getBlock(x, y, z).isWood(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
                isLog[i] = true;
            }
            i = i + 1;
        }
        return isLog;
    }

    public boolean[] isAdjacentBlockLeaves(World world, int x, int y, int z) {
        boolean[] isLeaves = new boolean[6];
        int i = 0;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (world.getBlock(x, y, z).isLeaves(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
                isLeaves[i] = true;
            }
            i = i + 1;
        }
        return isLeaves;
    }
}
