package sg.nus.iss.adproject.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.AppointmentStatusEnum;
import sg.nus.iss.adproject.repository.AppointmentRepository;



@Service
@Transactional(readOnly=true)
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired 
	private AppointmentRepository appointmentRepository;

	@Override
	public List<Appointment> findAllAppointments() {
		// TODO Auto-generated method stub
		return appointmentRepository.findAll();
	}
	
	@Transactional(readOnly=false)
	@Override
	public Appointment createAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment findAppointmentById(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.getAppointmentDetail(id);
	}

	@Override
	public List<Appointment> findAppointmentByPatientId(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentByPatientId(id);
	}

	@Override
	public List<Appointment> findAppointmentStatus(AppointmentStatusEnum status) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentStatus(status);
	}

	@Override
	public List<Appointment> findAppointmentByStaffId(int id) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentByStaffId(id);
	}

	@Transactional(readOnly=false)
	@Override
	public void removeAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		appointmentRepository.delete(appointment);
	}

	@Transactional(readOnly=true)
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
	
	@Override
	@Transactional
	public Appointment updateAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public List<Appointment> findAppointmentByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAppointmentByDate(date);
	}
	@Transactional(readOnly=false)
	@Override
	public void updateAppointmentDetails(int id,String medical_condition) {
		// TODO Auto-generated method stub
		
		appointmentRepository.updateAppointmentMedicalCondition(id,medical_condition);
		
	}




}
