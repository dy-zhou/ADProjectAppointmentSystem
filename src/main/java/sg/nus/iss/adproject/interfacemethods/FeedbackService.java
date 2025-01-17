package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Feedback;

public interface FeedbackService {

	List<Feedback> findAllFeedbacks();
	
	List<Feedback> findAllFeedbacksAndDoctorName();

	List<Feedback> findFeedbacksByStaffId(int id);

	Feedback getFeedbackDetail(int id);

	String getAllFeedbackDescriptionsByStaffId(int id);

	void deleteFeedbackById(int feedbackId);

	List<Feedback> reverseFeedbacks(int id);

}
 