package engenhariadesoftware.clinicacovid;

import java.util.Scanner;

public abstract class ModelController<T> {
	
	protected DataController dataController;
	
	protected Scanner in;
	
	protected T getUser() {
		T user = null;
		while(user == null) {
				print("Seu username: (Digite '0' para voltar) ");
				String username = in.next();
				
				if(username.equals("0")) {
					dataController.goToMenu();
					return null;
				}
				
				user = (T) getUser(username);
				if(user == null) {
					println("Username não encontrado");
					continue;
				}
				return user;
		}
		return null;
	}
	
	protected abstract T getUser(String username);
	
	protected void println(String str) {
		System.out.println(str);
	}
	
	protected void print(String str) {
		System.out.print(str);
	}

}
