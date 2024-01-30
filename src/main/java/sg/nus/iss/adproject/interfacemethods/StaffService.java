package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Staff;

public interface StaffService {
	Staff createStaff(Staff staff);
	Staff editStaff(Staff staff);
	List<Staff> findAllStaffs();
	List<Staff> findStaffById(int id);
	void deleteStaff(int id);
	

}
