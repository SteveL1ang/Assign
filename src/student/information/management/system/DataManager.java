/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.information.management.system;

/**
 *
 * @author 梁豪森
 */
import java.io.IOException;
import java.util.ArrayList;

public interface DataManager<T> {
    ArrayList<T> readFromFile(String fileName) throws IOException;
    void writeToFile(String fileName, ArrayList<T> items) throws IOException;
}
