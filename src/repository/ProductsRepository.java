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

import org.attoparser.config.ParseConfiguration;

import entities.Product;
import entities.Suppliers;

public class ProductsRepository {

	private final String URL = "jdbc:mysql://127.0.0.1:3306/kiskosar";
	private final String USERNAME = "root";
	private final String PASS = "";

	private Connection conn;

	public ProductsRepository() {
		this.initConnection();
	}

	private void initConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conn = DriverManager.getConnection(URL, USERNAME, PASS);
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection failed");
		}
	}

	public List<Product> findAllProduct() {
		String query = "SELECT * FROM product";
		List<Product> products = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setType(rs.getString("type"));
				product.setPrice(rs.getString("price"));
				products.add(product);
			}
		} catch (SQLException e) {
			System.err.println("Failure in findAllProduct() :" + e);
		} finally {
			closeStatement(rs, stmt);
		}
		return products;
	}
	
	public List<Product> findProductId(){
		String query = "SELECT id from product ORDER BY id DESC LIMIT 1";
		List<Product> products = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()){
				Product product = new Product();
				product.setId(rs.getInt("id"));
				products.add(product);
			}
		} catch (Exception e) {
			System.err.println("Failure in findProductId() :" + e);
		} finally {
			closeStatement(rs, stmt);
		}
		return products;
	}
	
	public void addNewProducts(Product product) {
		String query = "INSERT INTO product(name,type,price,supplier_price) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getType());
			pstmt.setInt(3, Integer.parseInt(product.getPrice()));
			pstmt.setInt(4, Integer.parseInt(product.getSupplierPrice()));
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in add new supplier: " + ex);
		}
	}
	
	public void addNewSuppliersProduct(Product product) {
		String query = "INSERT INTO suppliers_product(product_id,supplier_id) VALUES (?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, product.getId());
			pstmt.setInt(2, product.getSupplierId());
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in add new SuppliersProduct: " + ex);
		}
	}
	
	public void deleteUser(int id) throws SQLException {
		String query = "DELETE FROM product WHERE id = ?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			System.err.println("Failure in findAllProduct() :" + e);
		}
	}

	private void closeStatement(ResultSet rs, Statement stmt) {
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
