package engenhariadesoftware.clinicacovid.model;

import java.util.Date;

public class Appointment {
	private Date appointmentDate;
	private Patient patient;
	
	public Appointment(Date appointmentDate, Patient patient) {
		super();
		this.appointmentDate = appointmentDate;
		this.patient = patient;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}
	

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}
