package ca.ultracrepidarianism.kingdom.database.connections;

import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionType;

public abstract class ConnectionInfo {
    protected final String address;
    protected final String port;
    protected final String database;
    private final ConnectionType connectionType;
    private final String username;
    private final String password;

    ConnectionInfo(
            final ConnectionType connectionType,
            final String address,
            final String port,
            final String database,
            final String username,
            final String password
    ) {
        this.connectionType = connectionType;
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public abstract String getUrl();
}
