package test.engenhariadesoftware.clinicacovid.model;

import engenhariadesoftware.clinicacovid.model.Equipment;
import engenhariadesoftware.clinicacovid.model.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    Equipment seringa = new Equipment("seringa", 250, false);
    Equipment pcr = new Equipment("PCR", 300, true);

    Storage storage = new Storage();

    @Test
    void testAddEquipment(){

        storage.addEquipment(seringa);
        storage.addEquipment(pcr);

        assertEquals(2, storage.getEquipments().size());
    }

    @Test
    void testUseEquipments() throws Exception{

        storage.addEquipment(pcr);
        storage.useEquipments();

        assertEquals(1, storage.getEquipments().size());
        assertEquals(299, pcr.getQuantity());

    }

    @Test
    void testAddUnits() {
        storage.addEquipment(pcr);
        storage.addUnits(0, 10);

        assertEquals(310, pcr.getQuantity());
    }

    @Test
    void testMissingEquipments() {

        storage.addEquipment(seringa);
        storage.addEquipment(pcr);

        final var missingEquipments = storage.missingEquipments();

        assertEquals(1, missingEquipments.size());
        assertTrue(missingEquipments.contains(seringa));
    }

}
