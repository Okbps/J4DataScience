package ch02Acquisiotion;

import java.sql.*;

/**
 * Created by Aspire on 10.06.2017.
 */
public class DBUnderstanding {
    void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/j4ds?useSSL=false&serverTimezone=UTC";
            Connection connection = DriverManager.getConnection(url, "root", "root");

            String insertSQL = "INSERT INTO `j4ds`.`urltable` (`url`) VALUES (?);";
            PreparedStatement stmt = connection.prepareStatement(insertSQL);

            stmt.setString(1, "https://en.wikipedia.org/wiki/Data_science");
            stmt.execute();
            stmt.setString(1, "https://en.wikipedia.org/wiki/Bishop_Rock,_Isles_of_Scilly");
            stmt.execute();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM urltable");

            System.out.println("List of URLs");
            while(resultSet.next()){
                System.out.println(resultSet.getString(2));
            }

            statement.execute("TRUNCATE urltable;");

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
