package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.adproject.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	List<Patient> getAllPatients();
	
	Patient getPatientById(int id);
	
	  void deletePatientById(int id);
}
