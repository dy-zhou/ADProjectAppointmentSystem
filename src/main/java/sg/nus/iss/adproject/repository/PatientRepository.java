package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	@Query("Select p FROM Patient p WHERE p.id= :id")
	Patient getPatientById(@Param("id")int id);

}
