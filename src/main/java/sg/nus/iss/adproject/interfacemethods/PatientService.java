package sg.nus.iss.adproject.interfacemethods;

import sg.nus.iss.adproject.model.Patient;

public interface PatientService {
	Patient addNewPatient(Patient patient);
	Patient findPatientById(int id);
	Patient upadatePatientInfo(Patient patient);
}
