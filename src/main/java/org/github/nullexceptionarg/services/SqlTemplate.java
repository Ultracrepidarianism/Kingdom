package org.github.nullexceptionarg.services;

import org.bukkit.plugin.java.JavaPlugin;
import org.github.nullexceptionarg.Kingdom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class SqlInfo {
    public String IP;
    public int Port;
    public String Database;
    public String tableprefix;
    public String username;
    public String password;

    public SqlInfo() {
        IP = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.ip");
        Port = JavaPlugin.getPlugin(Kingdom.class).getConfig().getInt("DBserver.port");
        Database = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.database");
        tableprefix = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.tableprefix");
        username = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.username");
        password = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.password");
    }
}


public abstract class SqlTemplate {

    public static List<String> Tables = Arrays.asList("Town", "PlayerKD", "Claim", "TownUser");

    public static String getTown() {
        return "  CREATE TABLE IF NOT EXISTS `" + new SqlInfo().tableprefix + "Town` (\n" +
                "   `id` INT NOT NULL,\n" +
                "    `name` VARCHAR(255) NOT NULL,\n" +
                "    `owner` VARCHAR(36) NOT NULL,\n" +
                "    PRIMARY KEY (`id`)\n" +
                ")";
    }

    public static String getPlayer() {
        return "CREATE TABLE IF NOT EXISTS `" + new SqlInfo().tableprefix + "PlayerKD` (\n" +
                "`id` INT NOT NULL,\n" +
                "    `rank` VARCHAR(255) NOT NULL,\n" +
                "    `townid` INT NOT NULL,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    INDEX `FK_playerkd_town_townID_idx` (`townid` ASC) ,\n" +
                "    CONSTRAINT `FK_playerkd_town_townID` FOREIGN KEY (`townid`) REFERENCES `" + new SqlInfo().tableprefix + "Town` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ")";
    }

    public static String getClaim() {
        return "CREATE TABLE IF NOT EXISTS `" + new SqlInfo().tableprefix + "Claim` (\n" +
                "   `id` INT NOT NULL,\n" +
                "    `chunky` INT NOT NULL,\n" +
                "    `chunkx` INT NOT NULL,\n" +
                "    `townid` INT NOT NULL,\n" +
                "    `ownerid` INT NOT NULL,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    INDEX `FK_claim_town_townID_idx` (`townid` ASC) ,\n" +
                "    INDEX `FK_claim_playerkd_ownerID_idx` (`ownerid` ASC) ,\n" +
                "    CONSTRAINT `FK_claim_town_townID` FOREIGN KEY (`townid`) REFERENCES `" + new SqlInfo().tableprefix + "Town` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                "    CONSTRAINT `FK_claim_playerkd_ownerID` FOREIGN KEY (`ownerid`) REFERENCES `" + new SqlInfo().tableprefix + "PlayerKD` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ") \n";
    }

    public static String getTownPlayer() {
        return "CREATE TABLE IF NOT EXISTS `" + new SqlInfo().tableprefix + "TownUser` (\n" +
                "     `townid` INT NOT NULL,\n" +
                "    `playerid` INT NOT NULL,\n" +
                "    `permission` INT NOT NULL,\n" +
                "    PRIMARY KEY (`townid`, `playerid`),\n" +
                "    INDEX `FK_TownUser_Player_userID_idx` (`playerid` ASC) ,\n" +
                "    CONSTRAINT `FK_TownUser_Town_townID` FOREIGN KEY (`townid`) REFERENCES `" + new SqlInfo().tableprefix + "Town` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                "    CONSTRAINT `FK_TownUser_Player_userID` FOREIGN KEY (`playerid`) REFERENCES `" + new SqlInfo().tableprefix + "PlayerKD` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ")";
    }


}
