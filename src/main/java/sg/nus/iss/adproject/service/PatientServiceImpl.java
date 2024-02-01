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
		// TODO Auto-generated method stub
		return patientRepository.findById(id).get();
	}

	@Override
	public void addPatient(Patient patient) {
		// TODO Auto-generated method stub
		patientRepository.save(patient);
	}

	@Override
	public void updatePatient(int id, Patient patient) {
		// TODO Auto-generated method stub
		
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		if (optionalPatient.isPresent()) {
			Patient existingPatient = optionalPatient.get();
			existingPatient.setName(patient.getName());
			existingPatient.setAddress(patient.getAddress());
			existingPatient.setSex(patient.getSex());
			existingPatient.setAllergy(patient.getAllergy());
			existingPatient.setMedical_condition(patient.getMedical_condition());
			existingPatient.setAdditional_info(patient.getAdditional_info());

			patientRepository.save(existingPatient);
		} 
	}

	@Override
	public void deletePatient(int id) {
		// TODO Auto-generated method stub
		patientRepository.deleteById(id);
	}



}
