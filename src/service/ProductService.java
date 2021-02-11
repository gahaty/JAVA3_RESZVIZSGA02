package service;

import java.sql.SQLException;
import java.util.List;

import entities.Product;
import entities.Suppliers;
import repository.ProductsRepository;
import repository.SmallBasketRepository;

public class ProductService {
	
	private ProductsRepository repository;
	private SmallBasketRepository repo;
	
	public ProductService() {
		this.repository = new ProductsRepository();
		this.repo = new SmallBasketRepository();
	}
	
	public List<Product> listProducts(){
		return repository.findAllProduct();
	}
	
	public void addNewProducts(Product product) {
		repository.addNewProducts(product);
	}
	
	public void deleteUser(int id) throws SQLException {
		repository.deleteUser(id);
	}

}
