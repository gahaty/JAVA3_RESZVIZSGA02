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

public class SuppliersRepository {

	private final String URL = "jdbc:mysql://127.0.0.1:3306/kiskosar";
	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private Connection connection;

	public SuppliersRepository() {
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
				suppliers.setId(rs.getInt("id"));
				suppliers.setName(rs.getString("name"));
				suppliers.setContact(rs.getString("contact"));
				suppliers.setEmail(rs.getString("email"));
				suppliers.setPhone(rs.getInt("phone"));
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
			pstmt.setInt(4, suppliers.getPhone());
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in add new supplier: " + ex);
		}

	}

	public void updateSupplier(Suppliers supplier) {
		String query = "UPDATE suppliers SET name = ?, contact = ?, email = ?, phone = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, supplier.getName());
			pstmt.setString(2, supplier.getContact());
			pstmt.setString(3, supplier.getEmail());
			pstmt.setInt(4, supplier.getPhone());
			pstmt.setInt(5, supplier.getId());
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in update supplier " + ex);
		}
	}

	public void deleteSupplier(int id) {
		String query = "DELETE FROM suppliers WHERE id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in delete supplier " + ex);
		}
	}

	public Suppliers findSupplierById(int id) {
		String query = "SELECT * FROM suppliers WHERE id = ?";
		try {
			PreparedStatement prepStmt = connection.prepareStatement(query);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				Suppliers supplier = new Suppliers();
				supplier.setId(rs.getInt("id"));
				supplier.setName(rs.getString("name"));
				supplier.setContact(rs.getString("contact"));
				supplier.setEmail(rs.getString("email"));
				supplier.setPhone(rs.getInt("phone"));
				return supplier;
			}
		} catch (SQLException ex) {
			System.err.println("Error in findSupplierById() :" + ex);
		}
		return null;
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
