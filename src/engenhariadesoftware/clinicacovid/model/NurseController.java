package engenhariadesoftware.clinicacovid.model;

import engenhariadesoftware.clinicacovid.DataController;
import engenhariadesoftware.clinicacovid.GetUserInterface;
import engenhariadesoftware.clinicacovid.Main;

import java.util.Scanner;

public class NurseController {
	
	private static Scanner in;
	private static DataController dataController;
	private static Boolean handleNurseLoop;
	private static Nurse nurse;
	
	public static void handleNurse(DataController _dataController) {
		nurse = null;
		in = new Scanner(System.in);
		dataController = _dataController;
		handleNurseLoop = true;
		
		while(handleNurseLoop) {	
			nurse = getNurse();
			printMenu(nurse.getName());
			readOption();
		}
	}
	
	private static void printMenu(String nurseName) {
		System.out.println("Bem vindo(a) " + nurseName + "!");
		System.out.println("1. Ver Agenda");
		System.out.println("2. Ver Lista de Equipamentos");
		System.out.println("3. Voltar");
	}
	
	private static Boolean readOption() {
		int option = in.nextInt();
		switch(option) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				dataController.goToMenu();
				handleNurseLoop = false;
				break;
			default:
				System.out.println("Opção inválida");
				handleNurseLoop = false;
		}
		
		return true;
	}
	
	private static Nurse getNurse() {
		if(nurse == null) {
			nurse = Main.getUser(new GetUserInterface<Nurse>() {
				@Override
				public Nurse getUser(String username) {
					return dataController.getNurse(username);
				}
			});
			if(nurse == null) handleNurseLoop = false;
		}
		
		return nurse;
	}
}
