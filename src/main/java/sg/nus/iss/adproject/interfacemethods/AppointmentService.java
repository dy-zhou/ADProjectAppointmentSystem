package sg.nus.iss.adproject.interfacemethods;

import java.time.LocalDate;
import java.util.List;

import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.AppointmentStatusEnum;

public interface AppointmentService {
	List<Appointment> findAllAppointments();
	Appointment createAppointment(Appointment appointment);
	Appointment updateAppointmentStatus(Appointment appointment);
	Appointment getAppointmentDetail(int id);
	Appointment findAppointmentById(int id);
	List<Appointment> findAppointmentByPatientId(int id);
	List<Appointment> findAppointmentStatus(AppointmentStatusEnum status);
	List<Appointment> findAppointmentByStaffId(int id); 
	void removeAppointment(Appointment appointment);

	Appointment updateAppointment(Appointment appointment);

	List<Appointment> findAppointmentByDate(LocalDate date);
	void updateAppointmentDetails(int id, String medical_condition);

	
}
