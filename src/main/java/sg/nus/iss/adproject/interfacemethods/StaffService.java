package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Staff;

public interface StaffService {
	Staff createStaff(Staff staff);
	List<Staff> findAllStaffs();
	List<Staff> findStaffById(int id);
	List<Staff> findStaffByDepartmentId(int id);
	void deleteStaff(int id);
	Staff Authentication(String username,String password);

}
