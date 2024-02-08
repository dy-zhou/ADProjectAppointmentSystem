package sg.nus.iss.adproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.adproject.interfacemethods.DiseaseService;
import sg.nus.iss.adproject.model.Disease;
import sg.nus.iss.adproject.repository.DiseaseRepository;

@Service
public class DiseaseServiceImpl implements DiseaseService{
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	@Override
	public Disease findDepartmentByDiseaseName(String name) {
		return diseaseRepository.findDepartmentByDiseaseName(name);
	}
}
