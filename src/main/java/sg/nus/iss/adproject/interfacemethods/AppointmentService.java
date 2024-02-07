package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Appointment;

public interface AppointmentService {
	List<Appointment> findAllAppointments();
	Appointment createAppointment(Appointment appointment);
	Appointment updateAppointmentStatus(Appointment appointment);
	Appointment getAppointmentDetail(int id);
	List<Appointment> findAppointmentById(int id);
	List<Appointment> findAppointmentByPatientId(int id);
	List<Appointment> findAppointmentStatus(String status);
	List<Appointment> findAppointmentByStaffId(int id); 
	void removeAppointment(Appointment appointment);
	Appointment updateAppointment(Appointment appointment);
	
}
