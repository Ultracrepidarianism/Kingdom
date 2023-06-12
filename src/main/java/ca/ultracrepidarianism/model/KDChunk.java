package ca.ultracrepidarianism.model;

import jakarta.persistence.Entity;
import org.hibernate.service.spi.InjectService;

@Entity
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

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public static KDChunk parse(org.bukkit.Chunk chunk) {
        return new KDChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().isInstance(this.getClass())) {
            KDChunk chunk = (KDChunk) obj;

            return chunk.getWorld().equals(this.getWorld()) &
                    chunk.getX() == this.getX() &&
                    chunk.getZ() == this.getZ();
        }

        if (obj instanceof org.bukkit.Chunk chunk) {
            return chunk.getWorld().getName().equals(this.getWorld()) &
                    chunk.getX() == this.getX() &&
                    chunk.getZ() == this.getZ();
        }

        return false;
    }
}
