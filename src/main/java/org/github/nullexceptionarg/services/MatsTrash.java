package org.github.nullexceptionarg.services;

import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;
import org.github.nullexceptionarg.Kingdom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatsTrash {

    public static Connection connectDB() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://"+ Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.address")+":"+ Kingdom.getPlugin(Kingdom.class).getConfig().getInt("DB.port")+"/"+ Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.database")+
                    "?verifyServerCertificate=false"+
                    "&useSSL=true"+
                    "&requireSSL=true";
            System.out.println(url);
            con = DriverManager.getConnection(url,  Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.username"),  Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.password"));
            String SQL = "CREATE TABLE IF NOT EXISTS `bans` (\n" +
                    "  `ID` int(11) NOT NULL,\n" +
                    "  `REASON` varchar(255) NOT NULL,\n" +
                    "  `UUID` varchar(36) DEFAULT NULL,\n" +
                    "  `USERNAME` varchar(17) NOT NULL,\n" +
                    "  `BANDATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  `BANEND` datetime DEFAULT NULL,\n" +
                    "  `BANNEDBY` varchar(17) NOT NULL,\n" +
                    "  `SERVER` varchar(50) NOT NULL\n" +
                    ");";
            con.createStatement().execute(SQL);
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null,  Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.table"),
                    new String[] {"TABLE"});
            if (!res.next()) {
                System.out.println("Error creating or reading SQL table");
            }
            return con;
        } catch (SQLException e) {
            Kingdom.getPlugin(Kingdom.class).getLogger().info("Connection error!\n" + e.getMessage());
            e.printStackTrace();
            System.out.println("SQL might not be set up correctly please be sure to modify the config");
            if (con != null)
                try
                {
                    System.out.println("Crash-------------");
                    con.close();
                }
                catch (Exception b){}
        }
        return null;
    }

    public static ArrayList Select(String Where) throws SQLException {
        ArrayList out = new ArrayList();

        Statement req = connectDB().createStatement();
        String request = "select * from "+ Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.table");
        if (Where != null && !Where.isEmpty()){
            request += " where " + Where;
        }
        request += ";";
        ResultSet rep = req.executeQuery(request);
        if (rep != null) {
            while (rep.next()) {
                try{
                    out.add(new Ban(
                                rep.getInt("ID"),
                                rep.getString("UUID"),
                                rep.getString("USERNAME"),
                                rep.getString("REASON"),
                                rep.getString("BANNEDBY"),
                                rep.getTimestamp("BANEND"),
                                rep.getString("SERVER")
                        ));
            }catch (Exception e){
                    System.out.println("Create BD service line 88");
                }
            }
        }
        return out;

    }

    public static boolean Insert(Ban ban) throws SQLException {

    }





}

