package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Feedback;

public interface FeedbackService {
List<Feedback> findAllFeedbacks();
List<Feedback> findFeedbacksByStaffId(int id);

}
