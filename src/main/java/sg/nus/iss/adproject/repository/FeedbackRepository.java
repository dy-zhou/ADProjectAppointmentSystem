package sg.nus.iss.adproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.adproject.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{

}
