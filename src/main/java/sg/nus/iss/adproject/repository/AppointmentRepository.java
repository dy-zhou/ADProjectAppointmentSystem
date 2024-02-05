package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{
	
	@Query("Select a From Appointment a WHERE id=:id")
	Appointment getAppointmentDetail(@Param("id") int id);
	
	@Query("Select a From Appointment a WHERE id=:id")
	List<Appointment> findAppointmentById(@Param("id") int id);
	
	@Query("Select a From Appointment a WHERE a.patient.id=:id")
	List<Appointment> findAppointmentByPatientId(@Param("id") int id);
	
	@Query("Select a From Appointment a WHERE a.status=:status")
	List<Appointment> findAppointmentStatus(@Param("status")String status);
	
	@Query("Select a From Appointment a WHERE a.staff.id=:id")
	List<Appointment> findAppointmentByStaffId(@Param("id") int id); 
	

}
