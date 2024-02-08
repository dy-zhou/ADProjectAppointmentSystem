package sg.nus.iss.adproject.interfacemethods;

import sg.nus.iss.adproject.model.Disease;

public interface DiseaseService {
	Disease findDepartmentByDiseaseName(String name);
}
