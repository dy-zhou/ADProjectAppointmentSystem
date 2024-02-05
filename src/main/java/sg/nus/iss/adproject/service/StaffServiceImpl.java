package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.repository.StaffRepository;


@Service
@Transactional(readOnly=true)
public class StaffServiceImpl implements StaffService{

	@Autowired
	private StaffRepository staffRepository;
	
	
	@Transactional(readOnly=false)
	@Override
	public Staff createStaff(Staff staff) {
		// TODO Auto-generated method stub
		return staffRepository.save(staff);
	}


	@Override
	public List<Staff> findAllStaffs() {
		// TODO Auto-generated method stub
		return staffRepository.findAll();
	}

	@Override
	public Staff findStaffById(int id) {
		// TODO Auto-generated method stub
		return staffRepository.findStaffById(id);
	}

	
	@Transactional(readOnly=false)
	@Override
	public void deleteStaff(int id) {
		// TODO Auto-generated method stub
		staffRepository.deleteById(id);
	}


	@Override
	public Staff Authentication(String username, String password) {
		// TODO Auto-generated method stub
		return staffRepository.staffAuthentication(username, password);
	}


}
