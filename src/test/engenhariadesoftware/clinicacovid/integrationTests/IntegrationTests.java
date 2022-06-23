package test.engenhariadesoftware.clinicacovid.integrationTests;

import engenhariadesoftware.clinicacovid.model.*;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {
	
	private Doctor doctor;
	private Patient patient;
	private Storage storage;
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@BeforeEach
	protected void dataSetup() throws Exception {
		
	    patient = new Patient("José",  20, "jose@gmail.com", "31999999999", "jose");
	    doctor = new Doctor("AB1234", "Roberto", "roberto");
	    storage = new Storage();
	    storage.addEquipment(new Equipment("Máscara Descartável", 4, true));
	    storage.addEquipment(new Equipment("Teste Covid", 20, true));
	    storage.addEquipment(new Equipment("Álcool em gel", 10, true));
	    
	}

    public IntegrationTests() throws ParseException {
    }	

    @Test
    void testScheduleAppointmentAndSaveToFile() throws ParseException, IOException {
    	
    	Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-08");
        DoctorAppointment docAppointment = new DoctorAppointment(doctor, date, patient);
        doctor.getCalendar().getAppointments().add(docAppointment);
        Appointment newAppointment = doctor.getCalendar().getAppointments().get(0);
        folder.create();
        
        File fileToSave = folder.newFile("schedule_appointment.txt");
        newAppointment.saveToFile(fileToSave);
        
        final String fileContent = Files.readString(fileToSave.toPath());
        
        assertEquals(1, doctor.getCalendar().getAppointments().size());
        assertTrue(fileToSave.exists());
        assertTrue(fileContent.contains(newAppointment.getPatient().getName()));
        assertTrue(fileContent.contains(Integer.toString(newAppointment.getPatient().getAge())));
        assertTrue(fileContent.contains(newAppointment.getPatient().getEmail()));
    }
    
    @Test
    void testStorageEquipmentsSaveToFile() throws IOException {
    	
    	folder.create();
        File fileToSave = folder.newFile("storage_equipments.txt");
        
        storage.saveEquipmentsToFile(fileToSave);
        
        final String fileContent = Files.readString(fileToSave.toPath());
        assertTrue(fileToSave.exists());
        assertTrue(fileContent.contains("Teste Covid"));
        assertTrue(fileContent.contains("Álcool em gel"));
        assertTrue(fileContent.contains("Máscara Descartável"));
    }
}