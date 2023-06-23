package ca.ultracrepidarianism.kingdom.database.connections;

import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionTypes;
import org.apache.commons.lang3.NotImplementedException;

public class ConnectionFactory {
    public static ConnectionInfo get(ConnectionTypes type){
        return switch (type) {
            case MYSQL -> new SqlInfo();
            case POSTGRESQL, MARIADB, H2, SQLLITE, MONGODB -> throw new NotImplementedException();
            default -> throw new IllegalArgumentException();
        };
    }
}
