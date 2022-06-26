package engenhariadesoftware.clinicacovid.integrationTests;

import engenhariadesoftware.clinicacovid.model.*;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertThrows;
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
	    folder.create();
	}

    public IntegrationTests() throws ParseException {
    }
    
    @Test
    void testPacientScheduleAndCancelAppointment() throws Exception {
    	
    	// Scheduling Appointment
    	Date scheduleDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-08");
    	DoctorAppointment docAppointment = new DoctorAppointment(doctor, scheduleDate, patient);
    	doctor.getCalendar().getAppointments().add(docAppointment);
    	
    	// Canceling Appointment
    	Calendar doctorCalendar = doctor.getCalendar();
    	doctorCalendar.cancel(docAppointment);
    	
    	List<Appointment> doctorAppointments = doctor.getCalendar().getAppointments();
    	assertTrue(doctorAppointments.contains(docAppointment) == false);
    }
    
    @Test
    void testCancelAppointmentAndCheckingFilterDay() throws Exception {
    	
    	// Scheduling Appointment
    	Date scheduleDateOne = new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-08");
    	Date scheduleDateTwo = new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-15");
    	DoctorAppointment docAppointmentOne = new DoctorAppointment(doctor, scheduleDateOne, patient);
    	DoctorAppointment docAppointmentTwo = new DoctorAppointment(doctor, scheduleDateTwo, patient);
    	doctor.getCalendar().getAppointments().add(docAppointmentOne);
    	doctor.getCalendar().getAppointments().add(docAppointmentTwo);
    	
    	// Canceling Appointment
    	Calendar doctorCalendar = doctor.getCalendar();
    	doctorCalendar.cancel(docAppointmentOne);
    	
    	assertTrue(doctorCalendar.filterDay(scheduleDateOne).size() == 0);
    	assertTrue(doctorCalendar.filterDay(scheduleDateTwo).size() == 1);
    }
    
    @Test
    void testCovidTestWithoutEquipmentExceptionThrown() throws ParseException {
    	
    	// Scheduling Covid Test Appointment
    	Nurse nurse = new Nurse("Luana","luana");
    	Date scheduleDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15");
    	CovidTestAppointment covidTestAppointment = new CovidTestAppointment(nurse,scheduleDate,patient);
    	doctor.getCalendar().getAppointments().add(covidTestAppointment);
    	
    	// Setting Equipment quantity to zero
    	List<Equipment> storageEquipments = storage.getEquipments();
    	Equipment covidEquipment = storageEquipments.stream()
    		.filter(equipment -> "Teste Covid".equals(equipment.getName()))
    		.findAny()
    		.orElse(null);
    	
    	if(covidEquipment != null)
    		covidEquipment.setQuantity(0);
    	
    	// Using not available equipment
    	Exception exception = assertThrows(Exception.class, () -> {
    		covidEquipment.use();
        });
    	
    	String expectedMessage = "Equipamento sem unidades disponíveis ou não consumível";
    	String actualMessage = exception.getMessage();
    	
    	assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    void testScheduleAppointmentAndSaveToFile() throws ParseException, IOException {
    	
    	// Scheduling Appointment
    	Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-12");
        DoctorAppointment docAppointment = new DoctorAppointment(doctor, date, patient);
        doctor.getCalendar().getAppointments().add(docAppointment);
        Appointment newAppointment = doctor.getCalendar().getAppointments().get(0);
        
        //Saving to file
        File fileToSave = folder.newFile("schedule_appointment.txt");
        newAppointment.saveToFile(fileToSave);
        
        final String fileContent = Files.readString(fileToSave.toPath(), StandardCharsets.ISO_8859_1);
        
        assertEquals(1, doctor.getCalendar().getAppointments().size());
        assertTrue(fileToSave.exists());
        assertTrue(fileContent.contains(newAppointment.getPatient().getName()));
        assertTrue(fileContent.contains(Integer.toString(newAppointment.getPatient().getAge())));
        assertTrue(fileContent.contains(newAppointment.getPatient().getEmail()));
    }
    
    @Test
    void testStorageSaveEquipmentsToFile() throws IOException {
    	
    	//Saving to file
        File fileToSave = folder.newFile("storage_equipments.txt");
        
        storage.saveEquipmentsToFile(fileToSave);
        
        final String fileContent = Files.readString(fileToSave.toPath(), StandardCharsets.ISO_8859_1);
        assertTrue(fileToSave.exists());
        assertTrue(fileContent.contains("Teste Covid"));
        assertTrue(fileContent.contains("Álcool em gel"));
        assertTrue(fileContent.contains("Máscara Descartável"));
    }
}