package sg.nus.iss.adproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{
	@Query("SELECT dis FROM Disease dis WHERE dis.name=:name")
	Disease findDepartmentByDiseaseName(@Param("name") String name);
}
