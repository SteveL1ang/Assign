/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.information.management.system;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 梁豪森
 */
public class StudManagmentStore {
    dbManager dbManager;
    Connection conn;
    Statement statement;

    public StudManagmentStore() {
        dbManager = new dbManager();
        conn = dbManager.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        StudManagmentStore SMS = new StudManagmentStore();

        try {
            SMS.statement.addBatch("CREATE TABLE STUDENTS (STUDENT_ID VARCHAR(50), NAME VARCHAR(50), AGE VARCHAR(50), ADDRESS VARCHAR(50))");
            SMS.statement.addBatch("INSERT INTO STUDENTS VALUES ('001', 'Steve', '21', 'Auckland'),\n"
                    + "('002', 'Jack', '24', 'Auckland'),\n"
                    + "('003', 'John', '21', 'Wellington'),\n"
                    + "('004', 'Black', '21', 'Auckland'),\n"
                    + "('005', 'Bob', '26', 'Wellington'),\n"
                    );
            SMS.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        SMS.closeConnection();
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }
    
}
