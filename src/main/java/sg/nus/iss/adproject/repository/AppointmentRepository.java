package sg.nus.iss.adproject.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.AppointmentStatusEnum;


public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{
	
	@Query("Select a From Appointment a WHERE id=:id")
	Appointment getAppointmentDetail(@Param("id") int id);
	
	@Query("Select a From Appointment a WHERE id=:id")
	Appointment findAppointmentById(@Param("id") int id);
	
	@Query("Select a From Appointment a WHERE a.patient.id=:id")
	List<Appointment> findAppointmentByPatientId(@Param("id") int id);
	
	@Query("Select a From Appointment a WHERE a.status=:status")
	List<Appointment> findAppointmentStatus(@Param("status")AppointmentStatusEnum status);
	
	@Query("Select a From Appointment a WHERE a.staff.id=:id")
	List<Appointment> findAppointmentByStaffId(@Param("id") int id); 
	
	@Query("SELECT a FROM Appointment a WHERE a.date = :date")
	public List<Appointment> findAppointmentByDate(@Param("date")LocalDate date);
	
	@Modifying
	@Query("Update Appointment a set a.medical_condition= :medical_condition WHERE a.id= :id" )
	public void updateAppointmentMedicalCondition(@Param("id")int id,@Param("medical_condition")String medical_condition);
}
