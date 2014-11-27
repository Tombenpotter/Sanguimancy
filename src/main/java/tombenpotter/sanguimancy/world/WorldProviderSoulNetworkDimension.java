package tombenpotter.sanguimancy.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;

public class WorldProviderSoulNetworkDimension extends WorldProvider {

    @Override
    public void registerWorldChunkManager() {
        WorldChunkManager manager = new WorldChunkManagerHell(BiomeGenBase.plains, BiomeGenBase.deepOcean.rainfall);
        this.worldChunkMgr = manager;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderFlat(worldObj, worldObj.getSeed(), false, "SoulNetworkDimension");
    }

    @Override
    public String getDimensionName() {
        return "Soul Network Dimension " + String.valueOf(dimensionId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }
}
