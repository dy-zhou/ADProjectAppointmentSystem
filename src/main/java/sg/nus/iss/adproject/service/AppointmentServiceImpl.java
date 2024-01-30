package sg.nus.iss.adproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.repository.AppointmentRepository;




public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired 
	private AppointmentRepository appointmentRepository;

	@Override
	public List<Appointment> findAllAppointments() {
		// TODO Auto-generated method stub
		return appointmentRepository.findAll();
	}

	@Override
	public Appointment createAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		return appointmentRepository.save(appointment);
	}

	@Override
	public List<Appointment> findAppointmentById(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentById(id);
	}

	@Override
	public List<Appointment> findAppointmentByPatientId(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentByPatientId(id);
	}

	@Override
	public List<Appointment> findAppointmentStatus(String status) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentStatus(status);
	}

	@Override
	public List<Appointment> findAppointmentByStaffId(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentByStaffId(id);
	}

	@Override
	public void removeAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		appointmentRepository.delete(appointment);
	}

	@Override
	public Appointment updateAppointmentStatus(Appointment appointment) {
		// TODO Auto-generated method stub
		Optional<Appointment> oldAppointment = appointmentRepository.findById(appointment.getId());
		if(oldAppointment.isPresent()) {
			Appointment newAppointment = oldAppointment.get();
			newAppointment.setStatus(appointment.getStatus());
			
			appointmentRepository.save(newAppointment);
		}		
		return oldAppointment.get();
		
	}

	@Override
	public Appointment getAppointmentDetail(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.getAppointmentDetail(id);
	}

}
