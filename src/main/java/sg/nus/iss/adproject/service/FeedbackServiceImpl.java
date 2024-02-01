package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.model.Feedback;

@Service
@Transactional(readOnly=true)
public class FeedbackServiceImpl implements FeedbackService{

	@Override
	public List<Feedback> findAllFeedbacks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Feedback> findFeedbacksByStaffId(int id) {
		// TODO Auto-generated method stub
		return null;
	}


}
