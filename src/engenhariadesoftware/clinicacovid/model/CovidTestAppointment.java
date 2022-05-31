package engenhariadesoftware.clinicacovid.model;

import java.util.Date;

public class CovidTestAppointment extends Appointment {
	
	private Nurse nurse;

	public CovidTestAppointment(Nurse nurse, Date appointmentDate, Patient patient) {
		super(appointmentDate, patient);
		this.nurse = nurse;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}
