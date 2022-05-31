package engenhariadesoftware.clinicacovid.model;

public class Professional {
	
	private String name;
	private String username;
	private Calendar calendar;
	
	public Professional(String name, String username) {
		super();
		this.name = name;
		this.username = username;
		
		calendar = new Calendar();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
}
