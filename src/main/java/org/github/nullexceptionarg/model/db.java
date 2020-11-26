package org.github.nullexceptionarg.model;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.nullexceptionarg.Kingdom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class db {
    private static final Kingdom instance = JavaPlugin.getPlugin(Kingdom.class);
    private static Map<String,Town> playerTownMap = new HashMap<>();
    private static Map<String,Town> townMap = new HashMap<>();


    public static void createPlayer(String uuid){
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "players");
        dataFolder.mkdirs();
        File file = new File(dataFolder,uuid + ".yml");
        if(file.exists()) return;
        else{
            try{
                file.createNewFile();
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                fileConfiguration.set("town","");
                fileConfiguration.save(file);
                playerTownMap.put(uuid,null);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void createTown(Player ply, String townName){
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "towns");
        dataFolder.mkdirs();
        File file = new File(dataFolder,townName + ".yml");
        if(file.exists()){
            ply.sendMessage(Util.getMessage("error.town.alreadyexists"));
            return;
        }
        try{
            file.createNewFile();
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.set("town",townName);
            fileConfiguration.set("owner",ply.getUniqueId().toString());
            fileConfiguration.set("officers", new ArrayList<String>());
            fileConfiguration.set("members", new ArrayList<String>());
            fileConfiguration.set("claims", new ArrayList<String>());
            fileConfiguration.save(file);
            townMap.put(townName,new Town(fileConfiguration.getString("town"),
                    fileConfiguration.getString("owner"),
                    fileConfiguration.getStringList("claims"),
                    fileConfiguration.getStringList("officers"),
                    fileConfiguration.getStringList("members")));
            playerTownMap.put(ply.getUniqueId().toString(),townMap.get(townName));
            setPlayerTown(ply.getUniqueId().toString(),townName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createClaim(PlayerKD plyKD, String claimName){
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "claims");
        dataFolder.mkdirs();
        File file = new File(dataFolder,claimName + ".yml");
        Player ply  = plyKD.getPlayer();
        String[] splitTab = claimName.split(" ");
        String worldName = splitTab[0];
        String chunkName = splitTab[1];
        Integer chunkx = Integer.parseInt(chunkName.split("_")[0]);
        Integer chunkz = Integer.parseInt(chunkName.split("_")[1]);
        if(plyKD.getTown().getClaims().size() > 0){
            boolean nextTo = false;
            for(String claim : plyKD.getTown().getClaims()){
                if(claim.equalsIgnoreCase(worldName + " " + (chunkx +1) + "_" + (chunkz)) ||
                        claim.equalsIgnoreCase(worldName + " " + (chunkx) + "_" + (chunkz + 1)) ||
                        claim.equalsIgnoreCase(worldName + " " + (chunkx -1) + "_" + (chunkz)) ||
                        claim.equalsIgnoreCase(worldName + " " + (chunkx) + "_" + (chunkz - 1))){
                    nextTo = true;
                    break;
                }
            }
            if(!nextTo){
                ply.sendMessage(Util.getMessage("error.claim.nextTo"));
                return;
            }
        }


        if(file.exists()) {
            ply.sendMessage(Util.getMessage("error.claim.alreadyclaimed"));
            return;
        }
        try {
            file.createNewFile();
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            fileConfig.set("town", plyKD.getTown().getTownName());
            fileConfig.set("chunkx",ply.getLocation().getChunk().getX());
            fileConfig.set("chunkz",ply.getLocation().getChunk().getZ());
            fileConfig.set("owner", "");
            fileConfig.save(file);
            dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "towns");
            file = new File(dataFolder,plyKD.getTown().getTownName() + ".yml");
            fileConfig = YamlConfiguration.loadConfiguration(file);
            plyKD.getTown().addTownClaim(claimName);
            fileConfig.set("claims",plyKD.getTown().getClaims());
            fileConfig.save(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Town getTown(String townName){
        if(townMap.containsKey(townName)){
            return townMap.get(townName);
        }
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "towns");
        dataFolder.mkdirs();
        File file = new File(dataFolder,townName + ".yml");
        if(!file.exists()) return null;
        FileConfiguration fileConfiguration =  YamlConfiguration.loadConfiguration(file);
        Town town = new Town(fileConfiguration.getString("town"),
                fileConfiguration.getString("owner"),
                fileConfiguration.getStringList("claims"),
                fileConfiguration.getStringList("officers"),
                fileConfiguration.getStringList("members"));
        townMap.put(townName,town);
        return town;
    }

    public static PlayerKD getPlayer(Player ply){
        PlayerKD test;
        if(playerTownMap.containsKey(ply.getUniqueId().toString())){
            Town town = playerTownMap.get(ply.getUniqueId().toString());
            test = new PlayerKD(ply, town);
        }else{
            File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "players");
            dataFolder.mkdirs();
            File file = new File(dataFolder,ply.getUniqueId().toString() + ".yml");
            if(!file.exists()){
                createPlayer(ply.getUniqueId().toString());
                return new PlayerKD(ply,null);
            }

            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            String townName = fileConfiguration.getString("town");
            test = new PlayerKD(ply, (townName.length() > 0 ? getTown(townName) : null));
        }
        return test;
    }

    public static Town getTownfromPlayer(String uuid){

        if(playerTownMap.containsKey(uuid))
            return playerTownMap.get(uuid);

       PlayerKD ply = getPlayer(Bukkit.getPlayer(UUID.fromString(uuid)));
       return ply.getTown();
    }

    public static void setPlayerTown(String uuid, String townName ){
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "players");
        File file = new File(dataFolder,uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("town", townName);
        try{
            config.save(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addPlayerToMap(Player ply){
        PlayerKD uwu = getPlayer(ply);
        playerTownMap.put(ply.getUniqueId().toString(),uwu.getTown());
    }

    public static void removePlayerfromMap(Player ply){
        playerTownMap.remove(ply.getUniqueId().toString());
    }

}
