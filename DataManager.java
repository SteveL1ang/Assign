/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student_information_management_system;

/**
 *
 * @author 梁豪森
 */
import java.sql.SQLException;
import java.util.List;



public interface DataManager<T> {
    List<T> readFromDB() throws SQLException;
    void writeToDB(List<T> items) throws SQLException;
}
