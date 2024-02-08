package sg.nus.iss.adproject.interfacemethods;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Schedule;

public interface ScheduleService {
Schedule createShedule(Schedule schedule);
Schedule editSchedule(Schedule schedule);
List<Schedule> findAllSchedules();
List<Schedule> findSchedulesByStaff(int id);
void deleteSchedule(int id);
Integer findMaxPatientSlotByTimeStart(@Param("timeStart") LocalTime timeStart, int staffId);
}
