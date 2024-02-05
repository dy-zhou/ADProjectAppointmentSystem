package sg.nus.iss.adproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Department;

public interface DepartmentRepository extends JpaRepository <Department, Integer>{
	@Query("Select d FROM Department d WHERE d.disease.id= :id")
	Department findDepartmentByDiseaseID(@Param("id") int id);
	
	
}