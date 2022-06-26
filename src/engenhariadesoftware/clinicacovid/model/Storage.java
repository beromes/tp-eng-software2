package engenhariadesoftware.clinicacovid.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
	
	public File saveEquipmentsToFile(File fileToSave) throws IOException {
		
        FileWriter fstream = new FileWriter(fileToSave.getAbsolutePath(), StandardCharsets.ISO_8859_1,true);
        
        for(Integer i = 0; i < this.equipments.size(); i++) {
        	Equipment current_equipment = this.getEquipments().get(i);
	        fstream.write(current_equipment.getName());
	        fstream.write(current_equipment.getQuantity());
	        fstream.write(Boolean.toString(current_equipment.canUse()));
        }
        
        fstream.close();
        
        return fileToSave;
        
	}
}
