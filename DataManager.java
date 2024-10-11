
package student.information.management.system;

import java.io.IOException;
import java.util.List;

public interface DataManager<T> {
    List<T> readFromFile(String fileName) throws IOException;
    void writeToFile(String fileName, List<T> items) throws IOException;
}
