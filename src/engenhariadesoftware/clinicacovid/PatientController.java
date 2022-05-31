package engenhariadesoftware.clinicacovid;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import engenhariadesoftware.clinicacovid.model.CovidTestAppointment;
import engenhariadesoftware.clinicacovid.model.Doctor;
import engenhariadesoftware.clinicacovid.model.DoctorAppointment;
import engenhariadesoftware.clinicacovid.model.Patient;

public class PatientController extends ModelController<Patient> {

	PatientController(DataController dataController, Scanner in) {
		this.dataController = dataController;
		this.in = in; 
	}
	
	@Override
	protected Patient getUser(String username) {
		return this.dataController.getPatient(username);
	}
	
	public void handlePatient() {
		
		Patient patient = getUser();
		
		if (patient == null) {
			return;
		}

		handlePatientLoop: 
		while(true) {

			printPatientMenu(patient.getName());
						
			int option = in.nextInt();
			switch(option) {
				case 1:
					if(handleSeeAvailableTimesType(patient)) {
						continue;
					}
					break;
				case 2:
					handleSeeScheduledAppointments(patient);
					break;
				case 3:
					dataController.goToMenu();
					break handlePatientLoop;
				default:
					println("Opção inválida");
					break;
			}
		}
	}

	
	private void printPatientMenu(String name) {
		println("Bem vindo(a) " + name);
		println("1. Ver Horários Disponíveis");
		println("2. Ver Consultas marcadas");
		println("3. Voltar");
	}
	
	private void handleSeeScheduledAppointments(Patient patient) {
		List<DoctorAppointment> docAps = dataController.getDoctorAppointmentsFor(patient);
		List<CovidTestAppointment> covidTestAps = dataController.getCovidTestAppointmentsFor(patient);
		
		if(docAps.isEmpty() && covidTestAps.isEmpty()) {
			println("Nenhuma consulta marcada\n");
		}else {
			if(!covidTestAps.isEmpty()) {
				println("\nExames de Covid Marcados");
				for(CovidTestAppointment ap : covidTestAps) {
					println("Enfermeiro(a): " + ap.getNurse().getName() + ", horário: " + ap.getAppointmentDate().toLocaleString());
				}
				println("");
			}
			
			if(!docAps.isEmpty()) {
				println("\nConsultas Médicas Marcadas");
				for(DoctorAppointment ap : docAps) {
					println("Doutor(a): " + ap.getDoctor().getName() + ", horário: " + ap.getAppointmentDate().toLocaleString());
				}
				println("");
			}
		}
	}

	private boolean handleSeeAvailableTimesType(Patient patient) {
		ol:
		while(true) {
			println("Voce quer ver horários para?");
			println("1. Consulta médica");
			println("2. Exame de COVID");
			println("3. Voltar");
			
			int option = in.nextInt();
			switch(option) {
				case 1:
					if(handleSeeAvailableDoctors(patient)) {
						return true;
					}
					break;
				case 2:
					break;
				case 3:
					break ol;
				default:
					println("Opção inválida");
					continue;
			}
		}
		return false;
	}
	
	private boolean handleSeeAvailableDoctors(Patient patient) {
		while(true) {
			println("Qual médico voce prefere ser atendido?");
			List<Doctor> doctors = dataController.getDoctors();
			for(int i = 0; i < doctors.size(); i++) {
				println((i+1) + ". " + doctors.get(i).getName());
			}
			println((doctors.size()+1) + ". Voltar");
			
			int option = in.nextInt();
			if(option == doctors.size()+1) {
				break;
			}else if(option > doctors.size() + 1 || option < 1) {
				println("Opção inválida");
				continue;
			}else {
				if(handleSeeAvailableTime(patient, doctors.get(option-1))) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private boolean handleSeeAvailableTime(Patient patient, Doctor doctor) {
		while(true) {
			Date dateNext = new Date();
			dateNext.setHours(dateNext.getHours() + 1);
			dateNext.setMinutes(0);
			dateNext.setSeconds(0);
			
			List<Date> availableTimes = dataController.getAvailableTimesStarting(dateNext, doctor);
			for(int i = 0; i < availableTimes.size(); i++) {
				Date date = availableTimes.get(i);
				println((i+1) + ". Marcar para " + date.toLocaleString());
			}
			println((availableTimes.size() + 1) + ". Voltar");
			
			int option = in.nextInt();
			if(option == availableTimes.size()+1) {
				break;
			}else if(option > availableTimes.size() + 1 || option < 1) {
				println("Opção inválida");
				continue;
			}else {
				DoctorAppointment ap = new DoctorAppointment(doctor, availableTimes.get(option-1), patient);
				doctor.getCalendar().getAppointments().add(ap);
				println("Marcado com sucesso!");
				return true;
			}
		}
		return false;
	}
}
