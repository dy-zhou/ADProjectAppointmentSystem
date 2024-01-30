package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Staff;

public interface StaffRepository extends JpaRepository<Staff,Integer> {
	
	@Query("Select st from Staff st WHERE id= :id")
	List<Staff> findStaffById(@Param("id")int id);
}
