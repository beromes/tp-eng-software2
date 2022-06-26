package engenhariadesoftware.clinicacovid.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
	
	public File saveToFile(File fileToSave) throws IOException {
		
        FileWriter fstream = new FileWriter(fileToSave.getAbsolutePath(), StandardCharsets.ISO_8859_1, true);
        fstream.write(this.getAppointmentDate().toString());
        fstream.write(this.getPatient().getName());
        fstream.write(this.getPatient().getEmail());
        fstream.write(this.getPatient().getAge());
        fstream.close();
        
        return fileToSave;
        
	}
	
}
