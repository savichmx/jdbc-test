package com.savich.maksim;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://database-1.cf7g410jr7vi.eu-central-1.rds.amazonaws.com:3306/jdbc_test";
    private static final String USER = "admin";
    private static final String PASSWORD = "1qaz2wsx";
    private static final String END_OF_LINE_SEPARATOR = "END_OF_LINE";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            try {
                try (ResultSet result = statement.executeQuery("SELECT table_name, table_type, engine FROM information_schema.tables;")) {
                    logResults(result);
                }
            } finally {
                statement.close();
            }
        } catch (SQLException e) {
            logError(e.getMessage());
        }
    }

    private static void logResults(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int i = 1;
            StringBuilder resultString = new StringBuilder("|");
            while (!END_OF_LINE_SEPARATOR.equals(getResultStringByIndex(resultSet, i))) {
                resultString.append(String.format("%50s", getResultStringByIndex(resultSet, i)));
                resultString.append("|");
                i++;
            }
            System.out.println(resultString);
        }
    }

    private static void logError(String message) {
        System.out.printf("ERROR :: %s%n", message);
    }

    private static String getResultStringByIndex(ResultSet resultSet, int i) {
        try {
            return resultSet.getString(i);
        } catch (Exception e){
            return END_OF_LINE_SEPARATOR;
        }
    }
}
