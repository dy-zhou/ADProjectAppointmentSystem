package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.User;

public interface UserService {
	List<User> findUserById(int id);

}
