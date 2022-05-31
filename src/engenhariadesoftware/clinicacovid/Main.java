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

	private static DataController controller;
	private static Scanner in;
	private static PatientController patientController;
	
	public static void main(String[] args) {
		controller = new DataController();
		in = new Scanner(System.in);
		
		patientController = new PatientController(controller, in);
		
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
				DoctorController.handleDoctor();
				break;
			case Nurse:
				NurseController.handleNurse(controller);
				break;
			case Patient:
				patientController.handlePatient();
				break;
			default:
				controller.setMode(null);
				break;
		}
	}
	
	public static <T> T getUser(GetUserInterface<T> getUserInterface) {
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
	
	private static void println(String str) {
		System.out.println(str);
	}
	
	private static void print(String str) {
		System.out.print(str);
	}
}
