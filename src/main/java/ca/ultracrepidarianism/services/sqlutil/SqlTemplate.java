package ca.ultracrepidarianism.services.sqlutil;

import java.util.Arrays;
import java.util.List;


public class SqlTemplate {

    private SqlInfo info = new SqlInfo();
    private List<String> tables = Arrays.asList("Town", "Player", "Claim", "TownUser");

    public List<String> getTables() {
        return tables;
    }

    public String getTown() {
        return "  CREATE TABLE IF NOT EXISTS `" + info.getTablePrefix() + "Town` (\n" +
                "   `id` INT NOT NULL,\n" +
                "    `name` VARCHAR(255) NOT NULL,\n" +
                "    `owner` VARCHAR(36) NOT NULL,\n" +
                "    PRIMARY KEY (`id`)\n" +
                ")";
    }

    public String getPlayer() {
        return "CREATE TABLE IF NOT EXISTS `" + info.getTablePrefix() + "Player` (\n" +
                "`id` VARCHAR(32) NOT NULL,\n" +
                "    `rank` VARCHAR(255) NOT NULL,\n" +
                "    `townid` INT NOT NULL,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    INDEX `FK_playerkd_town_townID_idx` (`townid` ASC) ,\n" +
                "    CONSTRAINT `FK_playerkd_town_townID` FOREIGN KEY (`townid`) REFERENCES `" + info.getTablePrefix() + "Town` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ")";
    }

    public String getClaim() {
        return "CREATE TABLE IF NOT EXISTS `" + info.getTablePrefix() + "Claim` (\n" +
                "   `id` INT NOT NULL,\n" +
                "    `chunky` INT NOT NULL,\n" +
                "    `chunkx` INT NOT NULL,\n" +
                "    `townid` INT NOT NULL,\n" +
                "    `ownerid` INT NOT NULL,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    INDEX `FK_claim_town_townID_idx` (`townid` ASC) ,\n" +
                "    INDEX `FK_claim_playerkd_ownerID_idx` (`ownerid` ASC) ,\n" +
                "    CONSTRAINT `FK_claim_town_townID` FOREIGN KEY (`townid`) REFERENCES `" + info.getTablePrefix() + "Town` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                "    CONSTRAINT `FK_claim_playerkd_ownerID` FOREIGN KEY (`ownerid`) REFERENCES `" + info.getTablePrefix() + "PlayerKD` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ") \n";
    }

    public String getTownPlayer() {
        return "CREATE TABLE IF NOT EXISTS `" + info.getTablePrefix() + "TownUser` (\n" +
                "     `townid` INT NOT NULL,\n" +
                "    `playerid` INT NOT NULL,\n" +
                "    `permission` INT NOT NULL,\n" +
                "    PRIMARY KEY (`townid`, `playerid`),\n" +
                "    INDEX `FK_TownUser_Player_userID_idx` (`playerid` ASC) ,\n" +
                "    CONSTRAINT `FK_TownUser_Town_townID` FOREIGN KEY (`townid`) REFERENCES `" + info.getTablePrefix() + "Town` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                "    CONSTRAINT `FK_TownUser_Player_userID` FOREIGN KEY (`playerid`) REFERENCES `" + info.getTablePrefix() + "PlayerKD` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ")";
    }


}
