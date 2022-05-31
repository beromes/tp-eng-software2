package engenhariadesoftware.clinicacovid.model;

import java.util.Date;

public class DoctorAppointment extends Appointment {
	
	private Doctor doctor;

	public DoctorAppointment(Doctor doctor, Date appointmentDate, Patient patient) {
		super(appointmentDate, patient);
		this.doctor = doctor;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
