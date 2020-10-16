package org.github.nullexceptionarg.model;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;


public class Claim {
    private Integer chunkY;
    private Integer chunkX;
    private String town;
    private String ownerUID;

    public Claim(Integer chunkX,Integer chunkY, String town,  String ownerUID){
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.ownerUID = ownerUID;
        this.town = town;
    }

    public Integer getChunkX() {
        return chunkX;
    }

    public Integer getChunkY() {
        return chunkY;
    }

}
