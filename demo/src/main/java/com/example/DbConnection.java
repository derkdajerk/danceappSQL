/*
 * Derek Trauner
 * SPC_ID: 2491481
 * Program to be the connection part of the mySQL databse, also used for functions that complete reading from the database
 */

package com.example;

import java.sql.*;
import java.util.*;

public class DbConnection {
	private static Connection conn = null;

	static Connection getConnection() {
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
		return conn;
	}

	public static DataSetGeneric<danceClass> ReadFromDataBaseMDC() {
		DataSetGeneric<danceClass> classes = new DataSetGeneric<>();
		try {
			Connection conn = getConnection();
			if (conn == null) {
				System.err.println("No database connection");
				return null;
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM mdc");

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
		} catch (SQLException e) {
			System.err.println("Error reading from database: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static DataSetGeneric<danceClass> ReadFromDataBaseTMILLY() {
		DataSetGeneric<danceClass> classes = new DataSetGeneric<>();
		try {
			Connection conn = getConnection();
			if (conn == null) {
				System.err.println("No database connection");
				return null;
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM tmilly");

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

			System.out.println("Found " + count + " classes in TMilly table");

			if (count == 0) {
				System.err.println("No classes found in TMilly table");
				return null;
			}

			return classes;
		} catch (SQLException e) {
			System.err.println("Error reading from database: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static DataSetGeneric<danceClass> ReadFromDataBaseML() {
		DataSetGeneric<danceClass> classes = new DataSetGeneric<>();
		try {
			Connection conn = getConnection();
			if (conn == null) {
				System.err.println("No database connection");
				return null;
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ml");

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

			System.out.println("Found " + count + " classes in ML table");

			if (count == 0) {
				System.err.println("No classes found in ML table");
				return null;
			}

			return classes;
		} catch (SQLException e) {
			System.err.println("Error reading from database: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static DataSetGeneric<danceClass> ReadFromDataBaseTimeRange2(String startTime, String endTime) {
		DataSetGeneric<danceClass> classes = new DataSetGeneric<>();
		String[] tableNames = { "tmilly", "mdc", "ml" };
		Map<String, List<danceClass>> allResults = new HashMap<>();
		String date = null;

		try {
			Connection conn = getConnection();
			if (conn == null) {
				System.err.println("No database connection");
				return null;
			}

			for (String table : tableNames) {
				String query = String.format(
						"SELECT id, classname, instructor, price, time, length, date " +
								"FROM %s " +
								"WHERE STR_TO_DATE(time, '%%l:%%i %%p') " +
								"BETWEEN STR_TO_DATE(?, '%%l:%%i %%p') " +
								"AND STR_TO_DATE(?, '%%l:%%i %%p') " +
								"ORDER BY STR_TO_DATE(time, '%%l:%%i %%p')",
						table);

				try (PreparedStatement pstmt = conn.prepareStatement(query)) {
					pstmt.setString(1, startTime);
					pstmt.setString(2, endTime);
					ResultSet rs = pstmt.executeQuery();

					List<danceClass> tableResults = new ArrayList<>();
					while (rs.next()) {
						danceClass dance = new danceClass(rs.getInt("id"),
								rs.getString("classname"),
								rs.getString("instructor"),
								rs.getString("price"),
								rs.getString("time"),
								rs.getString("length"),
								rs.getString("date"));
						tableResults.add(dance);
						if (date == null) {
							date = rs.getString("date");
						}
					}

					if (!tableResults.isEmpty()) {
						allResults.put(table, tableResults);
					}
				}
			}
			// Add all results to the classes collection
			for (List<danceClass> tableClasses : allResults.values()) {
				for (danceClass dance : tableClasses) {
					classes.add(dance);
				}
			}
			return classes;

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
