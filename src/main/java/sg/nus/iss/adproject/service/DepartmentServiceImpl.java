package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sg.nus.iss.adproject.interfacemethods.DepartmentService;
import sg.nus.iss.adproject.model.Department;
import sg.nus.iss.adproject.repository.DepartmentRepository;

public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Department findDepartmentByDiseaseId(int id) {
		// TODO Auto-generated method stub
		return departmentRepository.findDepartmentByDiseaseID(id) ;
	}
	

}
