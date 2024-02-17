package sg.nus.iss.adproject.interfacemethods;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Staff;

public interface StaffService {
	Staff createStaff(Staff staff);
	List<Staff> findAllStaffs();
	Staff findStaffById(int id);
	List<Staff> findStaffByDepartmentId(int id);
	void deleteStaff(int id);
	Staff Authentication(String username,String password);
	//add this
	List<Staff> findAllDoctors();
	List<Staff>findStaffByDepartmentAndDesignation(int id, String designation);


}
