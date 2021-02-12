package service;

import entities.View;
import repository.ProductsRepository;


public class ViewService {	
	private ProductsRepository repository;

	public ViewService() {
		this.repository = new ProductsRepository();
	}
	
	public View viewList(int id) {
		return repository.viewList(id);
	}
}

