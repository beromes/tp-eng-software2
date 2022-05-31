package test.engenhariadesoftware.clinicacovid.model;

import engenhariadesoftware.clinicacovid.model.Appointment;
import engenhariadesoftware.clinicacovid.model.Calendar;
import engenhariadesoftware.clinicacovid.model.Patient;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTest {

    Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-08");

    Calendar calendar = new Calendar();

    Patient otavio = new Patient("Otavio", 21, "otavio@gmail.com", "31995541066", "otavio");

    Appointment appointment = new Appointment(date, otavio);


    public CalendarTest() throws ParseException {
    }

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

        final Appointment secondAppointment =
                new Appointment(new Date(),
                        new Patient("Bernardo", 21, "bernardo@gmail.com", "31999999999", "bernardo"));

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

        assertFalse(calendar.busyAt(new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-08")));
    }


}
