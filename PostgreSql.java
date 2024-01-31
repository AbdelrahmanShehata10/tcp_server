/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 20121
 */
public class PostgreSql {
List <user> users=new ArrayList<>();
        
    Connection ConneectToPostgresql() throws SQLException {
        String jdcURL = "jdbc:postgresql://localhost:5432/chatDB";
        String username = "chat";
        String password = "chat";

        Connection connection = DriverManager.getConnection(jdcURL, username, password);

        return connection;
    }

    void SelectfromPostgresql() throws SQLException {
        String[] info = new String[3];

        String sql = "SELECT * FROM _user";
        Statement statement = ConneectToPostgresql().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
         
users.add(new user(username,id));

            
        }

        ConneectToPostgresql().close();

    }

    void insertToDB(String name) throws SQLException {

        String sql = "INSERT INTO _user (username) VALUES (?)";

        try (Connection connection = ConneectToPostgresql(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            int DataAffected = preparedStatement.executeUpdate();

            if (DataAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    user getuser(String username) throws SQLException{
SelectfromPostgresql();
     for(user x:users){
if(x.getname().equals(username)){
    return x;
}
}
    return null;
     
    }

    void insertToDB( String msg_body, String sender_user) throws SQLException {
       int sender_id=getuser(sender_user).getid();
       
        String sql = "INSERT INTO logs (msg_body,sender_user,msg_time) VALUES (?,?,now())";

        try (Connection connection = ConneectToPostgresql(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, msg_body);
            preparedStatement.setInt(2, sender_id);

            int DataAffected = preparedStatement.executeUpdate();

            if (DataAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
