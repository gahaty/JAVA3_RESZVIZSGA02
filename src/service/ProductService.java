package service;

import java.util.List;

import entities.Product;
import repository.ProductsRepository;
import repository.SmallBasketRepository;

public class ProductService {
	
	private ProductsRepository repository;
	private SmallBasketRepository repo;
	
	public ProductService() {
		this.repository = new ProductsRepository();
		this.repo = new SmallBasketRepository();
	}
	
	public Product findProductById(int id) {
		return repository.findProductById(id);
	}
	
	public List<Product> listProducts(){
		return repository.findAllProduct();
	}
	
	public void addNewProducts(Product product) {
		repository.addNewProducts(product);
	}
	
	public void updateProduct(Product product) {
		repository.updateProduct(product);
	}
	
	public void updateSuppliersProduct(Product product) {
		repository.updateSuppliersProduct(product);
	}
	
	public void deleteProduct(int id) {
		repository.deleteProduct(id);
	}
	
	public void deleteSuppliersProduct(int id) {
		repository.deleteSuppliersProduct(id);
	}
	
	public Product findProductIdByMax() {
		return repository.findProductIdByMax();
	}
}
