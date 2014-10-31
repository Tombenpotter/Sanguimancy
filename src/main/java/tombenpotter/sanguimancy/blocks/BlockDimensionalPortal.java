package tombenpotter.sanguimancy.blocks;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
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
import net.minecraftforge.common.DimensionManager;
import tombenpotter.sanguimancy.tile.TileDimensionalPortal;
import tombenpotter.sanguimancy.util.LocationsHandler;
import tombenpotter.sanguimancy.util.PortalLocation;

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
                    if (linkedLocations.get(0).equals(new PortalLocation(tile.masterStoneX, tile.masterStoneY + 1, tile.masterStoneZ, world.provider.dimensionId))) {
                        PortalLocation linkedLocation = linkedLocations.get(1);
                        if (linkedLocation.dimension == world.provider.dimensionId) {
                            tile.teleportEntitySameDim(linkedLocation.x, linkedLocation.y, linkedLocation.z, entity);
                        } else {
                            tile.teleportEntityToDim(world, DimensionManager.getWorld(linkedLocation.dimension), linkedLocation.x, linkedLocation.y, linkedLocation.z, entity);
                        }
                    } else if (linkedLocations.get(1).equals(new PortalLocation(tile.masterStoneX, tile.masterStoneY + 1, tile.masterStoneZ, world.provider.dimensionId))) {
                        PortalLocation linkedLocation = linkedLocations.get(0);
                        if (linkedLocation.dimension == world.provider.dimensionId) {
                            tile.teleportEntitySameDim(linkedLocation.x, linkedLocation.y, linkedLocation.z, entity);
                        } else {
                            tile.teleportEntityToDim(world, DimensionManager.getWorld(linkedLocation.dimension), linkedLocation.x, linkedLocation.y, linkedLocation.z, entity);
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
}
