package sg.nus.iss.adproject.interfacemethods;


import java.util.List;
import java.util.Optional;

import sg.nus.iss.adproject.model.Department;

public interface DepartmentService {
	Department findDepartmentByDiseaseId(int id);
}
