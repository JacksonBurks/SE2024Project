package wheelOfFortune;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {
	private Connection conn;
	private FileInputStream fis;

	public Database() {
		try {
			// Create a properties object
			Properties prop = new Properties();

			// Use a FileInputStream as input to the Properties object for reading the db.properties file
			fis = new FileInputStream("lab7out/db.properties");
			prop.load(fis);

			// Get the username, password, and url
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String pass = prop.getProperty("password");

			// Set the conn object
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(url, user, pass);
		} catch (FileNotFoundException e) {
			// Properly handle file not found exception
			e.printStackTrace();
		} catch (IOException e) {
			// Properly handle IO exception
			e.printStackTrace();
		} catch (SQLException e) {
			// Properly handle SQL exception
			e.printStackTrace();
		} finally {
			// Close FileInputStream in finally block
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<String> query(String query)
	{
		//Add your code here
		ArrayList<String> result = new ArrayList<>();
		//Using the Conn object create a Statement object
		try {
			Statement stmt = conn.createStatement();

			//Using the statement object executeQuery using the input query (return the ResultSet)
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();

			//Use a while loop to process the rows = Create a comma , delimited record for each field
			while (rs.next()) {
				String record = "";
				for (int i = 1; i <= no_columns; i++) {
					// Append each column value to the record, separated by comma
					record += (rs.getString(i));
					if (i < no_columns) {
						// Add each comma , delimited record to the ArrayList
						record += ", ";
					}
				}
				// Add the record to the result list
				result.add(record);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// If no data found, return null
			return null;
		}		
		return result; //CHANGE
	}

	public void executeDML(String dml) throws SQLException
	{
		//Add your code here
		try {
			// 1. Use the Conn object to create a Statement object
			Statement stmt = conn.createStatement();

			// 2. Run dml on t the execute method of Statement
			stmt.executeUpdate(dml);
			stmt.close();

		} catch (SQLException e) {
			// Properly handle SQL exception
			e.printStackTrace();
			throw e; // Rethrow the exception to indicate failure
		}

	}

	public boolean accountExists(String username, String password) throws SQLException {
		// Define the SQL query string to check for the existence of the username
		String query = "SELECT * FROM User WHERE username = '" + username + "'";

		// Call the query method with the query string
		ArrayList<String> result = query(query);

		// Process the result to determine if the account exists
		if (result != null && !result.isEmpty()) {
			// If the result list is not empty, it means the account exists
			return true;
		} else {
			// If the result list is empty, it means the account does not exist
			String insertQuery = "INSERT INTO User (username, password) VALUES ('" + username + "', AES_ENCRYPT('" + password + "', 'key'))";
			try {
				// Execute the insert query
				executeDML(insertQuery);
				// Return true indicating the account was created successfully
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}


	public boolean verifyAccount(String username, String password) throws SQLException {
		// Define the SQL query string to check for the existence of the username and password
		String query = "SELECT AES_DECRYPT(password, 'key') AS decrypted_password FROM User WHERE username = '" + username + "'";

		// Call the query method with the query string
		ArrayList<String> result = query(query);

		// Process the result to determine if the account exists
		if (result != null && !result.isEmpty()) {
			// If the result list is not empty, it means the account exists
			return true;
		} else {
			// If the result list is empty, it means the account does not exist
			return false;
		}
	}
}

