package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Patient;

public interface PatientService {
	
	
	List<Patient> getAllPatients();

	Patient getPatientById(int id);

	void addPatient(Patient patient);

	void updatePatient(int id, Patient patient);

	void deletePatient(int id);
	
}
