package service;

import java.util.List;

import entities.Suppliers;
import repository.SuppliersRepository;

public class SuppliersService {

	private SuppliersRepository repository;

	public SuppliersService() {
		repository = new SuppliersRepository();
	}

	public List<Suppliers> listAllSuppliers() {
		return repository.listAllSuppliers();
	}

	public void addNewSuppliers(Suppliers suppliers) {
		repository.addNewSuppliers(suppliers);
	}

	public void deleteSupplier(int id) {
		repository.deleteSupplier(id);
	}

	public void updateSupplier(Suppliers supplier) {
		repository.updateSupplier(supplier);
	}

	public Suppliers findSupplierById(int id) {
		return repository.findSupplierById(id);
	}
}
