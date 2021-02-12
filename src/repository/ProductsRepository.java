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

import entities.Product;
import entities.View;

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

	public Product findProductIdByMax() {
		String query = "SELECT id from product ORDER BY id DESC LIMIT 1";
		Product product = new Product();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			product.setId(rs.getInt("id"));
		} catch (Exception e) {
			System.err.println("Failure in findProductIdByMax() :" + e);
		} finally {
			closeStatement(rs, stmt);
		}
		return product;
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
			System.err.println("Failure in addNewProducts() :" + ex);
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
			System.err.println("Failure in addNewSuppliersProduct() :" + ex);
		}
	}

	public Product findProductById(int id) {
		String query = "SELECT * FROM product WHERE id = ?";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setType(rs.getString("type"));
				product.setPrice(rs.getString("price"));
				product.setSupplierPrice(rs.getString("supplier_price"));
				return product;
			}
		} catch (SQLException ex) {
			System.err.println("Failure in findProductById() :" + ex);
		}
		return null;
	}
	
	public View viewList(int id) {
		String query = "SELECT product.name, product.supplier_price, product.price, suppliers.name, suppliers.contact, suppliers.email, suppliers.phone FROM suppliers_product INNER JOIN product ON suppliers_product.product_id = product.id INNER JOIN suppliers ON suppliers_product.supplier_id = suppliers.id WHERE product_id = ?";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(query);
			View view = new View();
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {	
				view.setName(rs.getString("product.name"));
				view.setSupplierPrice(rs.getInt("product.supplier_price"));
				view.setPrice(rs.getInt("product.price"));
				view.setSupplierName(rs.getString("suppliers.name"));
				view.setContact(rs.getString("suppliers.contact"));
				view.setEmail(rs.getString("suppliers.email"));
				view.setPhone(rs.getInt("suppliers.phone"));
				return view;
			}
		} catch (SQLException ex) {
			System.err.println("Failure in viewList() :" + ex);
		}
		return null;
	}

	public void deleteProduct(int id) {
		String query = "DELETE FROM product WHERE id = ?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			System.err.println("Failure in deleteProduct() :" + e);
		}
	}
	
	public void deleteSuppliersProduct(int id) {
		String query = "DELETE FROM suppliers_product WHERE product_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			System.err.println("Failure in deleteSuppliersProduct() :" + e);
		}
	}
	
	public void updateProduct(Product product) {
		String query = "UPDATE product SET name = ?, type = ?, price = ?, supplier_price = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getType());
			pstmt.setInt(3, Integer.parseInt(product.getPrice()));
			pstmt.setInt(4, Integer.parseInt(product.getSupplierPrice()));
			pstmt.setInt(5, product.getId());
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in updateProduct " + ex);
		}
	}
	
	public void updateSuppliersProduct(Product product) {
		String query = "UPDATE suppliers_product SET supplier_id = ? WHERE product_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, product.getSupplierId());
			pstmt.setInt(2, product.getId());
			pstmt.execute();
		} catch (SQLException ex) {
			System.err.println("Error in updateSuppliersProduct: " + ex);
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
