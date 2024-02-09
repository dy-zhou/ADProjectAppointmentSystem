package sg.nus.iss.adproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.adproject.interfacemethods.DepartmentService;
import sg.nus.iss.adproject.model.Department;
import sg.nus.iss.adproject.repository.DepartmentRepository;
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Department findDepartmentByDiseaseId(int id) {
		// TODO Auto-generated method stub
		return departmentRepository.findDepartmentByDiseaseId(id) ;
	}

	@Override
	public List<Department> findAllDepartments() {
		// TODO Auto-generated method stub
		return departmentRepository.findAll();
	}
	

}
