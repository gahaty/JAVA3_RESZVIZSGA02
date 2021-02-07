package service;

import java.util.List;

import entities.Suppliers;
import repository.SmallBasketRepository;

public class SmallBasketService {

	private SmallBasketRepository repository;

	public SmallBasketService() {
		repository = new SmallBasketRepository();
	}

	public List<Suppliers> listAllSuppliers() {
		return repository.listAllSuppliers();
	}
	
	public void addNewSuppliers(Suppliers suppliers) {
		repository.addNewSuppliers(suppliers);
	}

}
