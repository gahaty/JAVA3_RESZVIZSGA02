package entities;

public class Product {
	
	private Integer id;
	private String name;
	private String price;
	private String type;
	private Integer supplierId;
	private String supplierPrice;

	public Product() {}

	public Product(Integer id) {
		this.id = id;
	}
	
	public Product(Integer id, Integer supplierId) {
		this.id = id;
		this.supplierId = supplierId;
	}

	public Product(Integer id, String name, String price, String type, Integer supplierId, String supplierPrice) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.supplierId = supplierId;
		this.supplierPrice = supplierPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(String supplierPrice) {
		this.supplierPrice = supplierPrice;
	}
}
