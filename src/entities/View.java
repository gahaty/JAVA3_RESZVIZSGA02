package entities;

public class View {

	private String name;
	private Integer supplierPrice;
	private Integer price;
	private String supplierName;
	private String contact;
	private String email;
	private Integer phone;

	public View() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(Integer supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}
}
