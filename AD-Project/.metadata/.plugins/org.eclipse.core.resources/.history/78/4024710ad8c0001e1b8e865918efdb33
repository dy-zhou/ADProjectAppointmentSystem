package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("Select u FROM User u WHERE u.id= :id")
	List<User> findUserById(@Param("id")int id);
	
}
