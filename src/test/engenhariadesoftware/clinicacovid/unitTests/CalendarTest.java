package engenhariadesoftware.clinicacovid.unitTests;

import engenhariadesoftware.clinicacovid.model.Appointment;
import engenhariadesoftware.clinicacovid.model.Calendar;
import engenhariadesoftware.clinicacovid.model.Patient;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CalendarTest {

  Date date = new Date();
  Calendar calendar = new Calendar();
  Patient patient1 = new Patient("Fernando", 21, "fernando@gmail.com", "31998863120", "fernando");
  Appointment appointment = new Appointment(date, patient1);

  @Test
  void testScheduleAppointment() {
    calendar.schedule(appointment);

    final var appointments = calendar.getAppointments();
    assertTrue(appointments.contains(appointment));
  }

  @Test
  void testCancelAppointmentSuccess() throws Exception {
    calendar.schedule(appointment);
    calendar.cancel(appointment);
    final var appointments = calendar.getAppointments();
    assertTrue(appointments.isEmpty());
  }

  @Test
  void testCancelAppointmentFailure() throws Exception {
    Patient patient2 = new Patient("Bernardo", 21, "bernardo@gmail.com", "99999999999", "bernardo");

    final Appointment secondAppointment = new Appointment(new Date(), patient2);

    calendar.schedule(appointment);
    final Exception exception = assertThrows(Exception.class, () -> {
      calendar.cancel(secondAppointment);
    });

    assertEquals("Compromisso inexistente nesta agenda.", exception.getMessage());
  }

  @Test
  void testFilterDay() {
    calendar.schedule(appointment);
    final List<Appointment> appointments = calendar.filterDay(date);

    assertEquals(1, appointments.size());
  }

  @Test
  void testBusyAtSucess() {
    calendar.schedule(appointment);
    assertTrue(calendar.busyAt(date));
  }

  @Test
  void testBusyAtFailure() throws ParseException {
    calendar.schedule(appointment);
    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-06-25");
    assertFalse(calendar.busyAt(date2));
  }

}