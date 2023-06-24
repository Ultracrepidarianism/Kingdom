package ca.ultracrepidarianism.kingdom.database.models;

import jakarta.persistence.Embeddable;
import org.bukkit.Chunk;

import java.util.Objects;


@Embeddable
public class KDChunk {
    private String world;
    private int x;
    private int z;

    public KDChunk(final String world, final int x, final int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public KDChunk() {

    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public static KDChunk parse(final org.bukkit.Chunk chunk) {
        return new KDChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, z);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof final KDClaim kdClaim) {
            return x == kdClaim.getChunk().getX() && z == kdClaim.getChunk().getZ() && Objects.equals(world, kdClaim.getChunk().getWorld());
        }

        KDChunk kdChunk = null;
        if (o instanceof final KDChunk kdc) {
            kdChunk = kdc;
        } else if (o instanceof final Chunk c) {
            kdChunk = KDChunk.parse(c);
        } else {
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
