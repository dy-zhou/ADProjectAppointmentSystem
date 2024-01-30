package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sg.nus.iss.adproject.interfacemethods.UserService;
import sg.nus.iss.adproject.model.User;
import sg.nus.iss.adproject.repository.UserRepository;

public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public List<User> findUserById(int id) {
		// TODO Auto-generated method stub
		return userRepository.findUserById(id);
	}



}
