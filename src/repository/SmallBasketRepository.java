package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.Suppliers;

public class SmallBasketRepository {

	private final String URL = "jdbc:mysql://127.0.0.1:3306/kiskosar";
	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private Connection connection;

	public SmallBasketRepository() {
		initConnection();
	}

	private void initConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection failed!" + ex);
		}
	}

	public List<Suppliers> listAllSuppliers() {
		String query = "SELECT * FROM suppliers";
		List<Suppliers> resultSuppliers = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Suppliers suppliers = new Suppliers();
				suppliers.setName(rs.getString("name"));
				suppliers.setContact(rs.getString("contact"));
				suppliers.setEmail(rs.getString("email"));
				suppliers.setPhone(rs.getString("phone"));
				resultSuppliers.add(suppliers);
			}
		} catch (SQLException ex) {
			System.err.println("SQL ex in  listAllSuppliers: " + ex);
		} finally {
			closeStatment(rs, stmt);
		}

		return resultSuppliers;
	}

	public void addNewSuppliers(Suppliers suppliers) {
		String query = "INSERT INTO suppliers(name,contact,email,phone) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, suppliers.getName());
			pstmt.setString(2, suppliers.getContact());
			pstmt.setString(3, suppliers.getEmail());
			pstmt.setString(4, suppliers.getPhone());
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in add new supplier: " + ex);
		}

	}

	private void closeStatment(ResultSet rs, Statement stmt) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (Objects.nonNull(stmt)) {
				stmt.close();
			}
		} catch (SQLException ex) {
			System.err.println("SQL ex in finnaly " + ex);
		}
	}
}
