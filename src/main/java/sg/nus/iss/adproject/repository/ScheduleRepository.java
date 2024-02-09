package sg.nus.iss.adproject.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
	
	
	@Query("Select s From Schedule s WHERE staff.id= :id")
	List<Schedule> findSchedulesByStaff(@Param("id") int id);
	
	@Query(value = "SELECT MAX(patient_slot) FROM schedule WHERE time_start = :timeStart AND staff_id = :staffId AND date = :date", nativeQuery = true)
    Integer findMaxPatientSlotByTimeStart(@Param("timeStart") LocalTime timeStart,  @Param("staffId") int staffId, @Param("date")LocalDate date);
}
