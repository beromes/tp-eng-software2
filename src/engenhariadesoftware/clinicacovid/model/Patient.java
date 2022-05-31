package engenhariadesoftware.clinicacovid.model;

public class Patient {
	
	private String username;
	private String name;
	private int age;
	private String email;
	private String phone;
	
	
	public Patient(String name, int age, String email, String phone, String username) {
		super();
		this.username = username;
		this.name = name;
		this.age = age;
		this.email = email;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getUsername() {
		return username;
	}
	
}
