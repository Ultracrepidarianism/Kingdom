package ca.ultracrepidarianism.kingdom.database.connections;

import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionTypes;

public abstract class ConnectionInfo {
    public ConnectionTypes connectionType;
    public String address;
    public String port;
    public String database;
    public String username;
    public String password;

    ConnectionInfo(ConnectionTypes connectionType, String address, String port, String database, String username, String password) {
        this.connectionType = connectionType;
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
}
