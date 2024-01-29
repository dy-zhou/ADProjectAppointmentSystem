package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Appointment;

public interface MakeAppointmentService {
	Appointment makeAppointment(Appointment appoinment);
	Appointment updateAppointmentStatus();
	List<Appointment> findAppointmentById(int id);
	List<Appointment> findAppointmentByPatientId(int id);
	List<Appointment> findAppointmentSatus(String status);
	
	void removeAppointment(Appointment appointment);
	
}
