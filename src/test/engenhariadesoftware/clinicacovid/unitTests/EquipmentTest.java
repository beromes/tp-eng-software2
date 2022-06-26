package test.engenhariadesoftware.clinicacovid.unitTests;

import engenhariadesoftware.clinicacovid.model.Equipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {
  
  Equipment equipment = new Equipment("Seringa", 10, true);

  @Test
  void testUseEquipmentSuccess() throws Exception {
    equipment.use();
    assertEquals(9, equipment.getQuantity());
  }

  @Test
  void testUseEquipmentFailure() throws Exception {
    final Exception exception = assertThrows(Exception.class, () -> {
      for(int i = 0; i < 20; i++){
        equipment.use();
      }
    });

    assertEquals("Equipamento sem unidades disponíveis ou não consumível", exception.getMessage());
  }

  @Test
  void testAddUnits() {
    equipment.addUnits(21);

    assertEquals(31, equipment.getQuantity());
  }

  @Test
  void testCanUse() {
    if (equipment.getQuantity() > 0)
      assertTrue(equipment.canUse());
    else 
      assertFalse(equipment.canUse());
  }

  @Test
  void testNameEquipment() {
    assertEquals("Seringa", equipment.getName());
  }

}
