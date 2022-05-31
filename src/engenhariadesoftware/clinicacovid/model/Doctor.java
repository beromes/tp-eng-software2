package engenhariadesoftware.clinicacovid.model;


public class Doctor extends Professional{
	
	private String crm;
	
	public Doctor(String crm, String name, String username) {
		super(name, username);
		this.crm = crm;
	}
	
	public String getCrm() {
		return crm;
	}
}
