package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Feedback;

public interface FeedbackService {
List<Feedback>findAllFeedbacks();
List<Feedback> findFeedbackByType(String feedback_type);
List<Feedback> findFeedbackByDoctor();
void deleteFeedback(int id);


}
