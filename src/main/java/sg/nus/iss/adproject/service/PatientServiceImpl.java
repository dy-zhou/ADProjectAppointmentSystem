package sg.nus.iss.adproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import sg.nus.iss.adproject.interfacemethods.PatientService;
import sg.nus.iss.adproject.model.Patient;
import sg.nus.iss.adproject.repository.PatientRepository;

public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public List<Patient> getAllPatients() {
		// TODO Auto-generated method stub
		return patientRepository.findAll();
	}

	@Override
	public Patient getPatientById(int id) {
		return patientRepository.findById(id).get();
	}

	@Override
	public void addPatient(Patient patient) {
		// TODO Auto-generated method stub
		patientRepository.save(patient);
	}

	@Override

	public void updatePatient(int id, Patient newPatientInfo) {
		//// TODO Auto-generated method stub
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		if (optionalPatient.isPresent()) {
			Patient existingPatient = optionalPatient.get();
			existingPatient.setName(newPatientInfo.getName());
			existingPatient.setAddress(newPatientInfo.getAddress());
			existingPatient.setSex(newPatientInfo.getSex());
			existingPatient.setAllergy(newPatientInfo.getAllergy());
			existingPatient.setMedical_condition(newPatientInfo.getMedical_condition());
			existingPatient.setAdditional_info(newPatientInfo.getAdditional_info());

			patientRepository.save(existingPatient);
		} else {
			// SHOW ERROR HTML
		}
	}

	@Override
	public void deletePatientById(int id) {
		// TODO Auto-generated method stub
		patientRepository.deleteById(id);
	}

//	@Override
//	public Patient addNewPatient(Patient patient) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Patient findPatientById(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Patient updatePatientInfo(Patient patient) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
