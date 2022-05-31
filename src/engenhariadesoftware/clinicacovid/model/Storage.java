package engenhariadesoftware.clinicacovid.model;

import java.util.ArrayList;
import java.util.List;

public class Storage {
	private List<Equipment> equipments;
	
	public Storage() {
		equipments = new ArrayList<>();
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public List<Equipment> missingEquipments() {
		List<Equipment> missing = new ArrayList<>();
		for(Equipment eq : equipments) {
			if(!eq.canUse()) {
				missing.add(eq);
			}
		}
		return missing;
	}
	
	public void useEquipments() throws Exception {
		for(Equipment eq : equipments) {
			eq.use();
		}
	}
	
	public void addUnits(int eqIndex, int units) {
		equipments.get(eqIndex).addUnits(units);
	}
	
	public void addEquipment(Equipment eq) {
		equipments.add(eq);
	}
}
