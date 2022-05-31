package test.engenhariadesoftware.clinicacovid.integrationTests;

import engenhariadesoftware.clinicacovid.Controller;
import engenhariadesoftware.clinicacovid.model.Doctor;
import engenhariadesoftware.clinicacovid.model.DoctorAppointment;
import engenhariadesoftware.clinicacovid.model.Patient;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    Patient patient = new Patient("Otávio",  20, "vini@gmail.com", "31999999999", "otavio");
    Doctor doctor = new Doctor("AB1234", "Roberto", "roberto");

    Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-08");

    Controller controller = new Controller();

    public IntegrationTests() throws ParseException {
    }

    @Test
    void testScheduleAppointment() {

        DoctorAppointment docAppointment = new DoctorAppointment(doctor, date, patient);
        doctor.getCalendar().getAppointments().add(docAppointment);

        assertEquals(1, doctor.getCalendar().getAppointments().size());

    }

    @Test
    void testDoctorWithoutAppointments(){ assertEquals(0, doctor.getCalendar().getAppointments().size()); }

}