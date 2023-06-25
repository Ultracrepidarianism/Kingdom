package ca.ultracrepidarianism.kingdom.database.connections;

import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionType;
import org.apache.commons.lang3.NotImplementedException;

public class ConnectionFactory {
    public static ConnectionInfo get(ConnectionType type){
        return switch (type) {
            case MYSQL -> new MySqlInfo();
            case POSTGRESQL, MARIADB, H2, SQLLITE, MONGODB -> throw new NotImplementedException();
            default -> throw new IllegalArgumentException();
        };
    }
}
