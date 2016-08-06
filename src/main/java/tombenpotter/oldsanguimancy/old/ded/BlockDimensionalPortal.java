package tombenpotter.oldsanguimancy.old.ded;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.tile.TileDimensionalPortal;
import tombenpotter.oldsanguimancy.util.singletons.LocationsHandler;
import tombenpotter.oldsanguimancy.util.teleporting.PortalLocation;
import tombenpotter.oldsanguimancy.util.teleporting.TeleportingQueue;

import java.util.ArrayList;
import java.util.Random;

public class BlockDimensionalPortal extends BlockContainer {

    public BlockDimensionalPortal(Material material) {
        super(material);
        setBlockUnbreakable();
        setResistance(2000);
        setLightOpacity(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = AlchemicalWizardry.lifeEssenceFluid.getFlowingIcon();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileDimensionalPortal();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return 12;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileDimensionalPortal) {
            TileDimensionalPortal tile = (TileDimensionalPortal) world.getTileEntity(x, y, z);
            if (LocationsHandler.getLocationsHandler() != null) {
                ArrayList<PortalLocation> linkedLocations = LocationsHandler.getLocationsHandler().getLinkedLocations(tile.portalID);
                if (linkedLocations != null && !linkedLocations.isEmpty() && linkedLocations.size() > 1) {
                    if (world.getTileEntity(tile.masterStoneX, tile.masterStoneY, tile.masterStoneZ) != null && world.getTileEntity(tile.masterStoneX, tile.masterStoneY, tile.masterStoneZ) instanceof IMasterRitualStone) {
                        IMasterRitualStone masterRitualStone = (IMasterRitualStone) world.getTileEntity(tile.masterStoneX, tile.masterStoneY, tile.masterStoneZ);
                        if (linkedLocations.get(0).equals(new PortalLocation(tile.masterStoneX, tile.masterStoneY + 1, tile.masterStoneZ, world.provider.dimensionId))) {
                            PortalLocation linkedLocation = linkedLocations.get(1);
                            if (linkedLocation.dimension == world.provider.dimensionId) {
                                TeleportingQueue.getInstance().teleportSameDim(linkedLocation.x, linkedLocation.y, linkedLocation.z, entity, masterRitualStone.getOwner());
                            } else {
                                TeleportingQueue.getInstance().teleportToDim(world, linkedLocation.dimension, linkedLocation.x, linkedLocation.y, linkedLocation.z, entity, masterRitualStone.getOwner());
                            }
                        } else if (linkedLocations.get(1).equals(new PortalLocation(tile.masterStoneX, tile.masterStoneY + 1, tile.masterStoneZ, world.provider.dimensionId))) {
                            PortalLocation linkedLocation = linkedLocations.get(0);
                            if (linkedLocation.dimension == world.provider.dimensionId) {
                                TeleportingQueue.getInstance().teleportSameDim(linkedLocation.x, linkedLocation.y, linkedLocation.z, entity, masterRitualStone.getOwner());
                            } else {
                                TeleportingQueue.getInstance().teleportToDim(world, linkedLocation.dimension, linkedLocation.x, linkedLocation.y, linkedLocation.z, entity, masterRitualStone.getOwner());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta == 3) {
            setBlockBounds(0f, 0f, 0.375f, 1f, 1f, 0.625f);
        } else if (meta == 2) {
            setBlockBounds(0.375f, 0f, 0f, 0.625f, 1f, 1f);
        } else if (meta == 5) {
            setBlockBounds(0, 0.375f, 0f, 1f, 0.625f, 1f);
        } else {
            setBlockBounds(0f, 0f, 0f, 1f, 1, 1f);
        }
    }

    @Override
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0f, 0f, 0.375f, 1f, 1f, 0.625f);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        this.spawnParticles(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
    }

    private void spawnParticles(World world, int x, int y, int z) {
        Random random = world.rand;
        double d0 = 0.0625D;
        for (int l = 0; l < 6; ++l) {
            double d1 = (double) ((float) x + random.nextFloat());
            double d2 = (double) ((float) y + random.nextFloat());
            double d3 = (double) ((float) z + random.nextFloat());
            if (l == 0 && !world.getBlock(x, y + 1, z).isOpaqueCube()) {
                d2 = (double) (y + 1) + d0;
            }
            if (l == 1 && !world.getBlock(x, y - 1, z).isOpaqueCube()) {
                d2 = (double) (y + 0) - d0;
            }
            if (l == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube()) {
                d3 = (double) (z + 1) + d0;
            }
            if (l == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube()) {
                d3 = (double) (z + 0) - d0;
            }
            if (l == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube()) {
                d1 = (double) (x + 1) + d0;
            }
            if (l == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube()) {
                d1 = (double) (x + 0) - d0;
            }
            if (d1 < (double) x || d1 > (double) (x + 1) || d2 < 0.0D || d2 > (double) (y + 1) || d3 < (double) z || d3 > (double) (z + 1)) {
                world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
