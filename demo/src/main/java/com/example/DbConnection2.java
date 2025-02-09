/*
 * Derek Trauner
 * SPC_ID: 2491481
 * Program to be the connection part of the mySQL databse, also used for functions that complete reading from the database
 */

package com.example;

import java.sql.*;

public class DbConnection2 {
	private static Connection conn = null;

	static {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/danceappstorage",
						"dance",
						"5678");
				System.out.println("Database connection successful");
			} catch (SQLException e) {
				System.err.println("Connection error: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static DataSetGeneric<danceClass> ReadFromDataBaseMDC() {
		DataSetGeneric<danceClass> classes = new DataSetGeneric<>();
		try {
			if (conn == null || conn.isClosed()) {  // Quick check without timeout
				System.err.println("No valid database connection");
				return null;
			}

			try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM mdc")) {
					int count = 0;
					while (rs.next()) {
						count++;
						danceClass dance = new danceClass(
								rs.getInt("id"),
								rs.getString("classname"),
								rs.getString("instructor"),
								rs.getString("price"),
								rs.getString("time"),
								rs.getString("length"),
								rs.getString("date"));
						classes.add(dance);
					}

					System.out.println("Found " + count + " classes in MDC table");

					if (count == 0) {
						System.err.println("No classes found in MDC table");
						return null;
					}

					return classes;
				}				
			} catch (SQLException e) {
				System.err.println("Error reading from database: " + e.getMessage());
				e.printStackTrace();
				return null;
			}	
	}


	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Database connection closed");
			} catch (SQLException e) {
				System.err.println("Error closing connection: " + e.getMessage());
			}
		}
	}
}
