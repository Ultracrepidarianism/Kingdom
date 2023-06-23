package ca.ultracrepidarianism.kingdom.database;

import ca.ultracrepidarianism.kingdom.database.enums.ConnectionTypes;

public abstract class ConnectionInfo {
    public final ConnectionTypes connectionType;
    public final String address;
    public final String port;
    public final String database;
    public final String username;
    public final String password;

    public ConnectionInfo(ConnectionTypes connectionType, String address, String port, String database, String username, String password) {
        this.connectionType = connectionType;
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
}
