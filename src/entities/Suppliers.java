package entities;

public class Suppliers {

	private String name;
	private String email;
	private String contact;
	private String phone;

	public Suppliers() {

	}

	public Suppliers(String name, String email, String contact, String phone) {

		this.name = name;
		this.email = email;
		this.contact = contact;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Suppliers [name=" + name + ", email=" + email + ", contact=" + contact + ", phone=" + phone + "]";
	}

}
