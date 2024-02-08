package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.repository.FeedbackRepository;

@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public List<Feedback> findAllFeedbacks() {
		return feedbackRepository.findAll();
	}

	@Override
	public List<Feedback> findAllFeedbacksAndDoctorName() {
		return feedbackRepository.findAllFeedbacksAndDoctorName();
	}

	@Override
	public List<Feedback> findFeedbacksByStaffId(int id) {
		return feedbackRepository.findFeedbacksByStaffId(id);
	}

	@Override
	public Feedback getFeedbackDetail(int id) {
		return feedbackRepository.getFeedbackDetail(id);
	}

	@Override
	public List<Feedback> findTop15Feedbacks(int id) {
		return feedbackRepository.findTop15Feedbacks(id);
	}

	@Override
	public String getAllFeedbackDescriptionsByStaffId(int id) {
		List<Feedback> feedbackList = feedbackRepository.findFeedbacksByStaffId(id);

		StringBuilder allDescriptions = new StringBuilder();

		for (Feedback feedback : feedbackList) {
			allDescriptions.append(feedback.getDescription()).append(" ");
		}

		return allDescriptions.toString();
	}

	@Override
	@Transactional
	public void deleteFeedbackById(int feedbackId) {
		feedbackRepository.deleteById(feedbackId);
	}

}
