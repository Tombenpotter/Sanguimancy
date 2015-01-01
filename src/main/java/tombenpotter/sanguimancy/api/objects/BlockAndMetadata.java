package tombenpotter.sanguimancy.api.objects;

import net.minecraft.block.Block;

public class BlockAndMetadata {
    public Block block;
    public int metadata;

    public BlockAndMetadata(Block block) {
        this.block = block;
        this.metadata = 0;
    }

    public BlockAndMetadata(Block block, int meta) {
        this.block = block;
        this.metadata = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockAndMetadata that = (BlockAndMetadata) o;
        if (metadata != that.metadata) return false;
        if (block != null ? !block.equals(that.block) : that.block != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = block != null ? block.hashCode() : 0;
        result = 31 * result + metadata;
        return result;
    }

    @Override
    public String toString() {
        return "BlockAndMetadata{block=" + block + ", metadata=" + metadata + '}';
    }
}
