package engenhariadesoftware.clinicacovid;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import engenhariadesoftware.clinicacovid.model.Appointment;
import engenhariadesoftware.clinicacovid.model.Calendar;
import engenhariadesoftware.clinicacovid.model.CovidTestAppointment;
import engenhariadesoftware.clinicacovid.model.Doctor;
import engenhariadesoftware.clinicacovid.model.DoctorAppointment;
import engenhariadesoftware.clinicacovid.model.Nurse;
import engenhariadesoftware.clinicacovid.model.Patient;

public class Main {

	private static Controller controller;
	private static Scanner in;
	
	public static void main(String[] args) {
		controller = new Controller();
		in = new Scanner(System.in);
		
		while(true) {
			if(controller.currentMode == null) {
				HashMap<Mode, String> modes = controller.getModes();
				println("Voce é um?");
				
				
				Mode[] modesList = Mode.values();
				for(int i = 1; i <= modesList.length; i++) {
					println(i + ": " + modes.get(modesList[i-1]));
				}
				System.out.print("Digite a opção: ");
				int option = in.nextInt();
				if(option < 1 || option > modesList.length) {
					println("Opção inválida");
				}else {
					controller.setMode(modesList[option-1]);
				}
			}else {
				handleMode(controller.currentMode);
			}
		}
	}
	
	private static void handleMode(Mode mode) {
		switch(mode) {
			case Doctor:
				handleDoctor();
				break;
			case Nurse:
				handleNurse();
				break;
			case Patient:
				handlePatient();
				break;
			default:
				controller.setMode(null);
				break;
		}
	}
	
	private static <T> T getUser(GetUserInterface<T> getUserInterface) {
		T user = null;
		while(user == null) {
				print("Seu username: (Digite '0' para voltar) ");
				String username = in.next();
				
				if(username.equals("0")) {
					controller.goToMenu();
					return null;
				}
				
				user = (T) getUserInterface.getUser(username);
				if(user == null) {
					println("Username não encontrado");
					continue;
				}
				return user;
		}
		return null;
	}
	
	private static void handlePatient() {
		
		Patient patient = getPatient();
		
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
					controller.goToMenu();
					break handlePatientLoop;
				default:
					println("Opção inválida");
					break;
			}
		}
	}
	
	private static Patient getPatient() {
		Patient patient = getUser(new GetUserInterface<Patient>() {
			@Override
			public Patient getUser(String username) {
				return controller.getPatient(username);
			}
		});
		return patient;
	}
	
	private static void printPatientMenu(String name) {
		println("Bem vindo(a) " + name);
		println("1. Ver Horários Disponíveis");
		println("2. Ver Consultas marcadas");
		println("3. Voltar");
	}
	
	private static void handleSeeScheduledAppointments(Patient patient) {
		List<DoctorAppointment> docAps = controller.getDoctorAppointmentsFor(patient);
		List<CovidTestAppointment> covidTestAps = controller.getCovidTestAppointmentsFor(patient);
		
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

	private static boolean handleSeeAvailableTimesType(Patient patient) {
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
	
	private static boolean handleSeeAvailableDoctors(Patient patient) {
		while(true) {
			println("Qual médico voce prefere ser atendido?");
			List<Doctor> doctors = controller.getDoctors();
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
	private static boolean handleSeeAvailableTime(Patient patient, Doctor doctor) {
		while(true) {
			Date dateNext = new Date();
			dateNext.setHours(dateNext.getHours() + 1);
			dateNext.setMinutes(0);
			dateNext.setSeconds(0);
			
			List<Date> availableTimes = controller.getAvailableTimesStarting(dateNext, doctor);
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
	
	private static void handleNurse() {
		Nurse nurse = null;
		handleNurseLoop: 
		while(true) {	
			if(nurse == null) {
				nurse = getUser(new GetUserInterface<Nurse>() {
					@Override
					public Nurse getUser(String username) {
						return controller.getNurse(username);
					}
				});
				if(nurse == null) break;
			}
			
			println("Bem vindo(a) " + nurse.getName() + "!");
			println("1. Ver Agenda");
			println("2. Ver Lista de Equipamentos");
			println("3. Voltar");
			
			int option = in.nextInt();
			switch(option) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					controller.goToMenu();
					break handleNurseLoop;
				default:
					println("Opção inválida");
					continue;
			}
		}
	}
	
	private static void handleDoctor() {
		Doctor doctor = null;
		handleDocLoop: while(true) {		
			if(doctor == null) {
				doctor = getUser(new GetUserInterface<Doctor>() {
					@Override
					public Doctor getUser(String username) {
						return controller.getDoctor(username);
					}
				});
				if(doctor == null) break;
			}
			
			println("Bem vindo(a) " + doctor.getName() + "!");
			println("1. Ver Agenda");
			println("2. Voltar");
			
			int option = in.nextInt();	
			switch(option) {
				case 1:
					Calendar cal = doctor.getCalendar();
					List<Appointment> aps = cal.getAppointments();
					if(!aps.isEmpty() ) {
						println("Consultas Marcadas: ");
						for(Appointment ap : aps) {
							Patient pat = ap.getPatient();
							Date date = ap.getAppointmentDate();
							println(date.toLocaleString() + ": " 
									+ pat.getName() + " (" + pat.getPhone() + ")");
						}
						println("");
					}else {
						println("Não há consultas marcadas\n");
					}
					break;
				case 2:
					controller.goToMenu();
					break handleDocLoop;
				default:
					println("Opção inválida");
					continue;
			}
			
		}
	}
	
	private static void println(String str) {
		System.out.println(str);
	}
	
	private static void print(String str) {
		System.out.print(str);
	}
}
