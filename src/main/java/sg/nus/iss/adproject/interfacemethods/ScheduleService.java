package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Schedule;

public interface ScheduleService {
Schedule createShedule(Schedule schedule);
Schedule editSchedule(Schedule schedule);
List<Schedule> findAllSchedules();
List<Schedule> findSchedulesByStaff(int id);
void deleteSchedule(int id);

}
