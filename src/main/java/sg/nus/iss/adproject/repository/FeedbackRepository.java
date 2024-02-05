package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	@Query("SELECT f FROM Feedback f JOIN f.appointments a WHERE a.staff.id = :id")
	List<Feedback> findFeedbacksByStaffId(@Param("id") int id);

	@Query("SELECT f FROM Feedback f WHERE f.id=:id")
	Feedback getFeedbackDetail(@Param("id") int id);

	@Query("SELECT f, s.id AS staffId, s.name AS staffName FROM Feedback f "
	        + "JOIN f.appointments a "
	        + "JOIN a.staffs s")
	List<Feedback> findAllFeedbacksAndDoctorIdName();
	
	
	@Query("SELECT f FROM Feedback f JOIN f.appointments a WHERE a.staff.id = :id ORDER BY f.id DESC LIMIT 15" )
	List<Feedback> findTop15Feedbacks(@Param("id") int id);

}
