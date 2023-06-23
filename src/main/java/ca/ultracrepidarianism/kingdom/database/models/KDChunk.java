package ca.ultracrepidarianism.kingdom.database.models;

import org.bukkit.Chunk;

import java.util.Objects;

public class KDChunk {
    private String world;
    private int x;
    private int z;

    protected KDChunk() {}

    public KDChunk(String world, Integer x, Integer z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public static KDChunk parse(org.bukkit.Chunk chunk) {
        return new KDChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if(o instanceof KDClaim kdClaim){
            return x == kdClaim.getChunk().getX() && z == kdClaim.getChunk().getZ() && world == kdClaim.getChunk().getWorld();
        }

        KDChunk kdChunk = null;
        if(o instanceof KDChunk kdc){
            kdChunk = kdc;
        } else if (o instanceof Chunk c) {
            kdChunk = KDChunk.parse(c);
        }else{
            return false;
        }

        return x == kdChunk.x && z == kdChunk.z && Objects.equals(world, kdChunk.world);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(world, x, z);
//    }
//    @Override
//    public boolean equals(Object obj) {
//        if (obj.getClass().isInstance(this.getClass())) {
//            KDChunk chunk = (KDChunk) obj;
//
//            return chunk.getWorld().equals(this.getWorld()) &
//                    chunk.getX() == this.getX() &&
//                    chunk.getZ() == this.getZ();
//        }
//
//        if (obj instanceof org.bukkit.Chunk chunk) {
//            return chunk.getWorld().getName().equals(this.getWorld()) &
//                    chunk.getX() == this.getX() &&
//                    chunk.getZ() == this.getZ();
//        }
//
//        return false;
//    }
}
