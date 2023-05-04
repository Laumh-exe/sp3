package media;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISavable {
    public void create(Connection conn) throws SQLException;
    public int update(Connection conn) throws SQLException;
}
