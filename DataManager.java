
package student.information.management.system;

import java.sql.SQLException;
import java.util.List;

public interface DataManager<T> {
    List<T> readFromDB() throws SQLException;
    void writeToDB(List<T> items) throws SQLException;
}
