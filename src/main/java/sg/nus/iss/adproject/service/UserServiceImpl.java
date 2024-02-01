package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.adproject.interfacemethods.UserService;
import sg.nus.iss.adproject.model.User;
import sg.nus.iss.adproject.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public List<User> findUserById(int id) {
		// TODO Auto-generated method stub
		return userRepository.findUserById(id);
	}

	@Override
    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }


}
