package sg.nus.iss.adproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import sg.nus.iss.adproject.interfacemethods.ScheduleService;
import sg.nus.iss.adproject.model.Schedule;
import sg.nus.iss.adproject.repository.ScheduleRepository;

@Service
@Transactional(readOnly=true)
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Transactional(readOnly=false)
	@Override
	public Schedule createShedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return scheduleRepository.save(schedule);
	}

	@Transactional(readOnly=false)
	@Override
	public Schedule editSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		Optional<Schedule> oldSchedule=scheduleRepository.findById(schedule.getId());
		if(oldSchedule.isPresent()) {
			
			Schedule newSchedule=oldSchedule.get();
			newSchedule.setDate(schedule.getDate());
			newSchedule.setTime_start(schedule.getTime_start());
			newSchedule.setTime_end(schedule.getTime_end());
			
			scheduleRepository.save(newSchedule);
			}
		return oldSchedule.get();
	}

	
	@Override
	public List<Schedule> findAllSchedules() {
		// TODO Auto-generated method stub
		return scheduleRepository.findAll();
	}

	@Override
	public List<Schedule> findSchedulesByStaff(int id) {
		// TODO Auto-generated method stub
		return scheduleRepository.findSchedulesByStaff(id);
	}

	@Transactional(readOnly=false)
	@Override
	public void deleteSchedule(int id) {
		// TODO Auto-generated method stub
		scheduleRepository.deleteById(id);
	}

}
