package org.github.nullexceptionarg.services;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.model.PlayerKD;
import org.github.nullexceptionarg.model.Town;
import org.github.nullexceptionarg.model.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class YmlService implements IDatabase {

    private static final Kingdom instance = JavaPlugin.getPlugin(Kingdom.class);

    public YmlService(){}

    /**
     * Creates user's information if it doesn't exist.
     *
     * @param uuid User UUID
     */
    @Override
    public void createPlayer(String uuid) {
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "players");
        dataFolder.mkdirs();
        File file = new File(dataFolder, uuid + ".yml");
        if (file.exists()) return;
        else {
            try {
                file.createNewFile();
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                fileConfiguration.set("town", "");
                fileConfiguration.save(file);
                Kingdom.DB.playerTownMap.put(uuid, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    @Override
    public void createTown(Player ply, String townName) {
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "towns");
        dataFolder.mkdirs();
        File file = new File(dataFolder, townName + ".yml");
        if (file.exists()) {
            ply.sendMessage(Util.getMessage("error.town.alreadyexists"));
            return;
        }
        try {
            file.createNewFile();
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.set("town", townName);
            fileConfiguration.set("owner", ply.getUniqueId().toString());
            fileConfiguration.set("officers", new ArrayList<String>());
            fileConfiguration.set("members", new ArrayList<String>());
            fileConfiguration.set("claims", new ArrayList<String>());
            fileConfiguration.save(file);
            Kingdom.DB.townMap.put(townName, new Town(fileConfiguration.getString("town"),
                    fileConfiguration.getString("owner"),
                    fileConfiguration.getStringList("claims"),
                    fileConfiguration.getStringList("officers"),
                    fileConfiguration.getStringList("members")));
            Kingdom.DB.playerTownMap.put(ply.getUniqueId().toString(), Kingdom.DB.townMap.get(townName));
            setPlayerTown(ply.getUniqueId().toString(), townName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a chunk claim for the player's town.
     *
     * @param plyKD     Kingdom's entity for Players part of a Town.
     * @param claimName Claim name generated with the Player's world and coordinates.
     */
    @Override
    public void createClaim(PlayerKD plyKD, String claimName) {
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "claims");
        dataFolder.mkdirs();
        File file = new File(dataFolder, claimName + ".yml");
        Player ply = plyKD.getPlayer();
        String[] splitTab = claimName.split(" ");
        String worldName = splitTab[0];
        String chunkName = splitTab[1];
        Integer chunkx = Integer.parseInt(chunkName.split("_")[0]);
        Integer chunkz = Integer.parseInt(chunkName.split("_")[1]);
        if (plyKD.getTown().getClaims().size() > 0) {
            boolean nextTo = false;
            for (String claim : plyKD.getTown().getClaims()) {
                if (claim.equalsIgnoreCase(worldName + " " + (chunkx + 1) + "_" + (chunkz)) ||
                        claim.equalsIgnoreCase(worldName + " " + (chunkx) + "_" + (chunkz + 1)) ||
                        claim.equalsIgnoreCase(worldName + " " + (chunkx - 1) + "_" + (chunkz)) ||
                        claim.equalsIgnoreCase(worldName + " " + (chunkx) + "_" + (chunkz - 1))) {
                    nextTo = true;
                    break;
                }
            }
            if (!nextTo) {
                ply.sendMessage(Util.getMessage("error.claim.nextTo"));
                return;
            }
        }


        if (file.exists()) {
            ply.sendMessage(Util.getMessage("error.claim.alreadyclaimed"));
            return;
        }
        try {
            file.createNewFile();
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            fileConfig.set("town", plyKD.getTown().getTownName());
            fileConfig.set("chunkx", ply.getLocation().getChunk().getX());
            fileConfig.set("chunkz", ply.getLocation().getChunk().getZ());
            fileConfig.set("owner", "");
            fileConfig.save(file);
            dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "towns");
            file = new File(dataFolder, plyKD.getTown().getTownName() + ".yml");
            fileConfig = YamlConfiguration.loadConfiguration(file);
            plyKD.getTown().addTownClaim(claimName);
            fileConfig.set("claims", plyKD.getTown().getClaims());
            fileConfig.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    @Override
    public Town getTown(String townName) {
        if (Kingdom.DB.townMap.containsKey(townName)) {
            return Kingdom.DB.townMap.get(townName);
        }
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "towns");
        dataFolder.mkdirs();
        File file = new File(dataFolder, townName + ".yml");
        if (!file.exists()) return null;
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        Town town = new Town(fileConfiguration.getString("town"),
                fileConfiguration.getString("owner"),
                fileConfiguration.getStringList("claims"),
                fileConfiguration.getStringList("officers"),
                fileConfiguration.getStringList("members"));
        Kingdom.DB.townMap.put(townName, town);
        return town;
    }

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param ply Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    @Override
    public PlayerKD getPlayer(Player ply) {
        PlayerKD test;
        if (Kingdom.DB.playerTownMap.containsKey(ply.getUniqueId().toString())) {
            Town town = Kingdom.DB.playerTownMap.get(ply.getUniqueId().toString());
            test = new PlayerKD(ply, town);
        } else {
            File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "players");
            dataFolder.mkdirs();
            File file = new File(dataFolder, ply.getUniqueId().toString() + ".yml");
            if (!file.exists()) {
                createPlayer(ply.getUniqueId().toString());
                return new PlayerKD(ply, null);
            }

            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            String townName = fileConfiguration.getString("town");
            test = new PlayerKD(ply, (townName.length() > 0 ? getTown(townName) : null));
        }
        return test;
    }

    /**
     * Get the town a player is part of.
     *
     * @param uuid UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    @Override
    public Town getTownfromPlayer(String uuid) {
        if (Kingdom.DB.playerTownMap.containsKey(uuid))
            return Kingdom.DB.playerTownMap.get(uuid);

        PlayerKD ply = getPlayer(Bukkit.getPlayer(UUID.fromString(uuid)));
        return ply.getTown();
    }

    /**
     * Adds a player to the town.
     *
     * @param uuid     UUID of player you want to add to the town.
     * @param townName Name of down you want to add the Player to.
     */
    @Override
    public void setPlayerTown(String uuid, String townName) {
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "players");
        File file = new File(dataFolder, uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("town", townName);
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads PlayerKD information from Storage engine to RAM.
     *
     * @param ply Bukkit player entity.
     */
    @Override
    public void addPlayerToMap(Player ply) {
        PlayerKD uwu = getPlayer(ply);
        Kingdom.DB.playerTownMap.put(ply.getUniqueId().toString(), uwu.getTown());
    }

    /**
     * Unloads player from RAM to Storage engine.
     *
     * @param ply
     */
    @Override
    public void removePlayerfromMap(Player ply) {
        Kingdom.DB.playerTownMap.remove(ply.getUniqueId().toString());
    }

    /**
     * @param displayName
     * @param townName
     */
    @Override
    public void addPendingInvite(String displayName, String townName) {
        /* Bonjour, oui ^^^ */
    }
}
