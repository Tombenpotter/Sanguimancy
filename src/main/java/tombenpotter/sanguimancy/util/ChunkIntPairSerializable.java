package tombenpotter.sanguimancy.util;

import java.io.Serializable;

public class ChunkIntPairSerializable implements Serializable {

    public int chunkXPos;
    public int chunkZPos;

    public ChunkIntPairSerializable(int xCoord, int zCoord) {
        chunkXPos = xCoord;
        chunkZPos = zCoord;
    }

    @Override
    public int hashCode() {
        return chunkXPos * 1201 + this.chunkZPos * 0112;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ChunkIntPairSerializable ? (((ChunkIntPairSerializable) o).chunkXPos == this.chunkXPos && ((ChunkIntPairSerializable) o).chunkZPos == this.chunkZPos) : false;
    }

    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZPos() {
        return (this.chunkZPos << 4) + 8;
    }

    @Override
    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}
